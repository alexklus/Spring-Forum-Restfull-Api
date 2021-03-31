package com.omega.backend.forum.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto{
	
	private Long id;
	
	@NotBlank(message = "Comment filed cannot be empty!")
	private String content;
	
	private String username;
	
	
}
