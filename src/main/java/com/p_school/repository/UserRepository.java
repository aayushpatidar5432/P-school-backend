package com.p_school.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.p_school.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	  boolean existsByEmail(String email);
	  Optional<User> findByEmail(String userEmail);
}
