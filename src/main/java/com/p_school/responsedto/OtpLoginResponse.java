package com.p_school.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
	public class OtpLoginResponse {

	    private boolean otpRequired;

	    private String tempToken;

	    private String message;

	    // Getter Setter
	}

