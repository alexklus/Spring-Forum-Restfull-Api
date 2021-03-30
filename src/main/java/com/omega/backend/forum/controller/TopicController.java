package com.omega.backend.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omega.backend.forum.dto.TopicDto;
import com.omega.backend.forum.dto.request.TopicPageRequest;
import com.omega.backend.forum.dto.response.TopicPageResponse;
import com.omega.backend.forum.dto.response.TopicResponse;
import com.omega.backend.forum.model.service.TopicService;

@RestController
@RequestMapping("/api/topics")

public class TopicController {

	@Autowired
	private TopicService topicService;

	@PostMapping
	public ResponseEntity<TopicResponse> saveTopic(@Valid @RequestBody TopicDto topicDto) {

		return ResponseEntity.status(HttpStatus.CREATED).body(topicService.save(topicDto));
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<TopicPageResponse> findPaginated(
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "size", required = true) Integer size ) {
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(topicService.getPaginatedPosts(page,size));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET )
	public ResponseEntity<TopicResponse> getTopicById(@PathVariable("id") long id) {
		
		return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopicById(id));
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteTopicById(@PathVariable("id") long id){
		
		try {
			topicService.deleteById(id);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Comment have been deleted!");
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT )
	public ResponseEntity<TopicDto> editTopicName(@Valid @RequestBody TopicDto topicDto,@PathVariable("id") long id){
		try {
			topicService.edit(id, topicDto);
			
		}catch (Exception e) {
			
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(topicDto);
		}
		return ResponseEntity.status(HttpStatus.OK).body(topicDto);
	}
}
