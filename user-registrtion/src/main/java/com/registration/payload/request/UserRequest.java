package com.registration.payload.request;


import com.registration.util.Constants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	@Pattern(regexp = Constants.NAME_REGEXP, message = Constants.VALID_USER_NAME)
	@NotBlank(message = Constants.REQUIRE_AUTHOR_NAME)
	private String firstName;
	
	@Pattern(regexp = Constants.NAME_REGEXP, message = Constants.VALID_USER_NAME)
	@NotBlank(message = Constants.REQUIRE_AUTHOR_NAME)
	private String lastName;
	
	@Email(regexp = Constants.EMAIL_REGEXP, message = Constants.VALID_EMAIL)
	@NotBlank(message = Constants.REQUIRE_EMAIL)
	private String email;

	@Pattern(regexp = Constants.PASSWORD_REGEXP, message = Constants.VALID_PASSWORD)
	@NotBlank(message = Constants.REQUIRE_PASSWORD)
	private String password;
	
	@Pattern(regexp = Constants.CONTACT_REGEXP, message = Constants.VALID_CONTACT)
	@NotBlank(message = Constants.REQUIRE_CONTACT)
	private String phoneNumber;
	
	@Pattern(regexp = Constants.STATUS_REGEXP, message = Constants.VALID_STATUS)
	private String status;
	
	@Pattern(regexp = Constants.ROLES_REGEXP, message = Constants.VALID_ROLES)
	private String roles;
}