package com.omega.backend.forum.dto.response;

import java.util.List;

import com.omega.backend.forum.model.entity.Topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicPageResponse {
	
	private List<TopicResponse> content;
	
	private int page;
	
	private int size;
	
	private int totalElements;
	
	private boolean lastPage;
	
	
}
