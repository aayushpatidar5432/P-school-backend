package com.p_school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

	

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	//@Async
	public void sendOtp(String email, String otp) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setTo(email);
		message.setSubject("P-School OTP Verification");

		message.setText("Dear User,\n\n" + "Your OTP is: " + otp + "\n\nThis OTP is valid for 5 minutes."
				+ "\n\nThank You,\nP-School Team");

		mailSender.send(message);

	}

}
