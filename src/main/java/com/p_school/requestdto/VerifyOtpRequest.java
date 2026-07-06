package com.p_school.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VerifyOtpRequest {

    private String tempToken;

    private String otp;

    // Getter Setter

}