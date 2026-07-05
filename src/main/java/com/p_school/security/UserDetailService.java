package com.p_school.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.p_school.entity.User;
import com.p_school.enums.UserStatus;
import com.p_school.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		
		System.err.println("login step 1 ");
		
		User user = userRepository.findByEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new RuntimeException("User is inactive");
		}

		return user;
	}

}
