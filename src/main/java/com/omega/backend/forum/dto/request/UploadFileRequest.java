package com.omega.backend.forum.dto.request;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileRequest {
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private MultipartFile file;
	
}
