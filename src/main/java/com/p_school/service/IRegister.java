package com.p_school.service;

import com.p_school.entity.LoginResponse;

public interface IRegister {

	LoginResponse login(String email, String pass);

	LoginResponse refreshToken(String request);

}
