package com.omega.backend.forum.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	
	@NotEmpty(message = "Name should not be empty!")
	private String username;
	
	@NotEmpty(message = "email should not be empty!")
	private String email;
	
	@NotEmpty(message = "Password should not be empty!")
	private String password;
	
	@NotEmpty(message = "PasswordConfirmation should not be empty!")
	private String passwordConfirmation;


	
}
