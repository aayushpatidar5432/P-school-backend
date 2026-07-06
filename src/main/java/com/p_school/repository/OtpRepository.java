package com.p_school.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.p_school.entity.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long> {

	Optional<Otp> findByEmail(String email);

}