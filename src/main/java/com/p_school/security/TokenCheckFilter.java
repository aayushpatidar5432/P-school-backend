package com.p_school.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenCheckFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Authorization Header
		String authHeader = request.getHeader("Authorization");
             System.err.println("login step 2 ");
		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {

			String token = authHeader.substring(7);

			if (jwtUtil.validateToken(token)) {

				String email = jwtUtil.extractUsername(token);
			     System.err.println("login step 3 ");
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
			     System.err.println("login step 4 ");
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			     System.err.println("login step 5 ");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			     System.err.println("login step 6 ");
			}
		}
	     System.err.println("login step end ");
		filterChain.doFilter(request, response);
	}
}