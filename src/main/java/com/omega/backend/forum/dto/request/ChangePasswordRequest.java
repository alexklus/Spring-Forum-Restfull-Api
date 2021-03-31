package com.omega.backend.forum.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChangePasswordRequest {
	
	@NotBlank(message = "USERNAME FIELD CANNOT BE EMPTY!")
	private String userName;
	
	@NotBlank(message = "PASSWORD FIELD CANNOT BE EMPTY")
	private String currentPassword;
	
	@NotBlank(message = "NEW PASSWORD FILED CANNOT BE EMPTY")
	private String newPassword;
	
	
}
