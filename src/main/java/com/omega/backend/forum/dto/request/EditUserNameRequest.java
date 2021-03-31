package com.omega.backend.forum.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserNameRequest {
	
	@NotBlank(message = "CURRENT USERNAME REQUIRED!")
	private String currentUserName;
	
	@NotBlank(message = "NEW USERNAME FIELD REQUIRED!")
	private String newUserName;
}
