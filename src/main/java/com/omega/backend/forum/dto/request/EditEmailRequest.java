package com.omega.backend.forum.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditEmailRequest {
	@NotBlank(message = "EMAIL FIELD CANNOT BE EMPTY!")
	private String currentEmail;
	@NotBlank(message = "NEW EMAIL FIELD CANNOT BE EMPTY")
	private String newEmail;
}
