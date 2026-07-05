package com.p_school.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.p_school.entity.LoginResponse;
import com.p_school.entity.User;
import com.p_school.security.JwtUtil;
import com.p_school.security.RefreshToken;
import com.p_school.security.RefreshTokenRepository;
import com.p_school.service.IRegister;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IRegister {

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public LoginResponse login(String email, String password) {

		// 1. Authenticate User
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		User user = (User) authentication.getPrincipal();

		// 2. Generate Access Token
		String accessToken = jwtUtil.generateToken(user.getEmail());

		// 3. Generate Refresh Token
		String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

		// 4. Agar user ka purana refresh token hai to delete kar do
		refreshTokenRepository.findByUser(user).ifPresent(refreshTokenRepository::delete);

		// Ya seedha bhi kar sakte ho:
		// refreshTokenRepository.deleteByUser(user);

		// 5. Save New Refresh Token
		RefreshToken token = new RefreshToken();
		token.setToken(refreshToken);
		token.setUser(user);
		token.setCreatedAt(LocalDateTime.now());
		token.setExpiryDate(LocalDateTime.now().plusDays(7));
		token.setRevoked(false);

		refreshTokenRepository.save(token);

		// 6. Return Response
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
