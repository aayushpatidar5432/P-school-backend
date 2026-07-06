package com.p_school.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	// Base64 encoded secret key
	private static final String SECRET_KEY = "VGhpc0lzTXlTdXBlclNlY3JldEtleUZvckpXVFRva2VuMTIzNDU2Nzg5MDEyMzQ1";

	// 1 Minute
	private static final long EXPIRATION = 60 * 1000;

	private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 Days

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// Generate JWT Token
	public String generateToken(String email) {

		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	// Generate Refresh Token
	public String generateRefreshToken(String email) {

		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	// Extract Username (Email)
	public String extractUsername(String token) {

		return extractClaims(token).getSubject();
	}

	// Validate Token
	public boolean validateToken(String token) {

		return extractClaims(token).getExpiration().after(new Date());
	}

	// Extract Claims
	private Claims extractClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	public String extractEmail(String token) {

		Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

		return claims.getSubject();
	}

	public String generateOtpToken(String email) {

		return Jwts.builder().setSubject(email).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}
}