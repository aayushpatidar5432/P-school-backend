package com.p_school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PSchoolBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PSchoolBackendApplication.class, args);
	}

}
