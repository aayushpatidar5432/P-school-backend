package com.p_school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p_school.entity.LoginResponse;
import com.p_school.security.RefreshToken;
import com.p_school.security.RefreshTokenRepository;
import com.p_school.service.IRegister;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final IRegister register;

	private final RefreshTokenRepository refreshTokenRepository;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestParam String email, @RequestParam String password) {
		System.out.println("Controller Hit");
		LoginResponse loginres = register.login(email, password);

		System.err.println(loginres.getRefreshToken());

		return ResponseEntity.ok(loginres);
	}

	@PostMapping("/refresh/{request}")
	public LoginResponse refresh(@PathVariable String request) {

		return register.refreshToken(request);

	}

	@PostMapping("/logout/{refreshToken}")
	public String logout(@PathVariable String refreshToken) {

		RefreshToken refreshToke = refreshTokenRepository.findByToken(refreshToken)
				.orElseThrow(() -> new RuntimeException("Refresh Token Not Found"));
		refreshToke.setRevoked(true);

		refreshTokenRepository.save(refreshToke);

		return "Logout Successfully";
	}

}
