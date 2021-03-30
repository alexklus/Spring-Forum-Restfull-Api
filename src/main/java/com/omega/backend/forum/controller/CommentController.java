package com.omega.backend.forum.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.omega.backend.forum.dto.CommentDto;

@RestController
@RequestMapping("/api/topics/{topicId}/comments")
public class CommentController {
	
	@RequestMapping(method = RequestMethod.POST)
	public void addComment(@RequestBody CommentDto comment) {
		
	}
	
	@RequestMapping
	public void getPaginatedComments(@PathVariable("topicId") Long topicId) {
		
	}
	
}
