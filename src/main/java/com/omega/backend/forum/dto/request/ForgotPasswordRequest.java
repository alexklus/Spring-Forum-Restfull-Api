package com.omega.backend.forum.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
	
	@NotBlank(message = "USERNAME FIELD CANNOT BE EMPTY!")
	private String userName;
	
	@NotBlank(message = "EMAIL FIELD CANNOT BE EMPTY!")
	private String email;

}
