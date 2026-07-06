package com.p_school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.p_school.security.TokenCheckFilter;
import com.p_school.security.UserDetailService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

	private final TokenCheckFilter tokenCheckFilter;

	private final UserDetailService userDetailService;

	// private final AuthenticationSuccessHandler oAuth2SuccessHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailService);

		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.addFilterBefore(tokenCheckFilter, UsernamePasswordAuthenticationFilter.class)
//				.oauth2Login(oauth -> oauth.successHandler(oAuth2SuccessHandler))
				.authenticationProvider(authenticationProvider()) // ye class bta ti hai konsa provider use kar na hai
																	// loginke liye
				.httpBasic(http -> System.out.println(http.getClass().getName())).build();

	}

//  .requestMatchers("/api/admin/**").hasRole("ADMIN")// 1. Role Check
//	.requestMatchers("/api/user/update").hasAuthority("USER_UPDATE").//2. Permission Check
//	requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")//3. Multiple Roles

}
