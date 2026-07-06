package com.p_school.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.p_school.entity.Otp;
import com.p_school.repository.OtpRepository;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public void saveOtp(String email,String otp){

        otpRepository.findByEmail(email)
                .ifPresent(otpRepository::delete);

        Otp entity=new Otp();

        entity.setEmail(email);

        entity.setOtp(otp);

        entity.setExpiryTime(
                LocalDateTime.now().plusMinutes(5));

        otpRepository.save(entity);

    }

    public void verifyOtp(String email,String otp){

        Otp entity=otpRepository.findByEmail(email)
                .orElseThrow();

        if(entity.getExpiryTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP Expired");

        if(!entity.getOtp().equals(otp))
            throw new RuntimeException("Invalid OTP");

    }

    public void deleteOtp(String email){

        otpRepository.findByEmail(email)
                .ifPresent(otpRepository::delete);

    }

}