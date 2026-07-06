package com.p_school.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.p_school.entity.LoginResponse;
import com.p_school.entity.User;
import com.p_school.repository.RefreshTokenRepository;
import com.p_school.repository.UserRepository;
import com.p_school.requestdto.VerifyOtpRequest;
import com.p_school.responsedto.OtpLoginResponse;
import com.p_school.security.JwtUtil;
import com.p_school.security.RefreshToken;
import com.p_school.service.IRegister;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

class AuthServiceImpl implements IRegister {

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	private final RefreshTokenRepository refreshTokenRepository;

	private final EmailService emailService;

	private final UserRepository userRepository;

	private OtpService otpService;

	@Override
	public OtpLoginResponse login(String email, String password) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		User user = (User) authentication.getPrincipal();

		// Generate OTP
		String otp = String.valueOf((int) ((Math.random() * 900000) + 100000));

		// pervious is email se opt tha remove kar diya or new opt set kar diya
		otpService.saveOtp(user.getEmail(), otp);

		emailService.sendOtp(user.getEmail(), otp);

		String tempToken = jwtUtil.generateOtpToken(user.getEmail());

		return new OtpLoginResponse(true, tempToken, "OTP Sent Successfully");
	}

	@Override
	public LoginResponse verifyOtp(VerifyOtpRequest request) {

		String email = jwtUtil.extractEmail(request.getTempToken());

		otpService.verifyOtp(email, request.getOtp());

		User user = userRepository.findByEmail(email).orElseThrow();

		String accessToken = jwtUtil.generateToken(email);

		String refreshToken = jwtUtil.generateRefreshToken(email);

		refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);

		RefreshToken token = new RefreshToken();

		token.setToken(refreshToken);

		token.setUser(user);

		token.setCreatedAt(LocalDateTime.now());

		token.setExpiryDate(LocalDateTime.now().plusDays(7));

		token.setRevoked(false);

		refreshTokenRepository.save(token);

		otpService.deleteOtp(email);

		return new LoginResponse(accessToken, refreshToken);

	}

	@Override
	public LoginResponse refreshToken(String request) {

		RefreshToken refreshToken = refreshTokenRepository.findByToken(request)
				.orElseThrow(() -> new RuntimeException("Refresh Token Not Found"));

		if (refreshToken.getRevoked()) {
			throw new RuntimeException("Refresh Token Revoked");
		}

		if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Refresh Token Expired");
		}

		String email = jwtUtil.extractUsername(refreshToken.getToken());

		String newAccessToken = jwtUtil.generateToken(email);

		return new LoginResponse(newAccessToken, refreshToken.getToken());
	}

}
