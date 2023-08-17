package com.registration.payload.request;

import com.registration.util.Constants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
	@Email(regexp = Constants.EMAIL_REGEXP, message = Constants.VALID_EMAIL)
	@NotBlank(message = Constants.REQUIRE_EMAIL)
	private String email;
	@NotBlank(message = Constants.REQUIRE_PASSWORD)
	private String password;
}
