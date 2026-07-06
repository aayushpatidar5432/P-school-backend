package com.p_school.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Otp {

	@Id
	@GeneratedValue
	private Long id;

	private String email;

	private String otp;

	private LocalDateTime expiryTime;

	  public LocalDateTime getExpiryTime() {
	        return expiryTime;
	    }

	    public void setExpiryTime(LocalDateTime expiryTime) {
	        this.expiryTime = expiryTime;
	    }

}