package com.omega.backend.forum.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto{
	private Long id;
	@NotBlank(message = "Title field cannot be empty!")
	private String title;
	@NotBlank(message = "Comment filed cannot be empty!")
	private String content;
	
}
