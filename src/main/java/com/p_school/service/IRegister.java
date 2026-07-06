package com.p_school.service;

import com.p_school.entity.LoginResponse;
import com.p_school.requestdto.VerifyOtpRequest;
import com.p_school.responsedto.OtpLoginResponse;

public interface IRegister {

	OtpLoginResponse login(String email, String pass);

	LoginResponse refreshToken(String request);

	LoginResponse verifyOtp(VerifyOtpRequest request);

}
