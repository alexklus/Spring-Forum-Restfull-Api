package com.omega.backend.forum.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
	
	private Long id;
	
	private String content;
	
	private Long topicId;
	
	private String userName;
	
	
}
