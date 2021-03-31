package com.omega.backend.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omega.backend.forum.dto.CommentDto;
import com.omega.backend.forum.dto.response.CommentResponse;
import com.omega.backend.forum.model.service.CommentService;

@RestController
@RequestMapping("/api/topics/{topicId}/comments")
public class CommentController {
	/////////TODO add like functionality
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CommentResponse> addComment(@Validated @RequestBody CommentDto commentDto,
			@PathVariable("topicId") long topicId) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(commentService.save(commentDto, topicId));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable("topicId") Long topicId,
			@RequestParam(value = "page", required = true) Integer page,
			@RequestParam(value = "size", required = true) Integer size) {
		
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(topicId, page, size));
		
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> editComment(@PathVariable("id") long commentId, @RequestBody CommentDto commentDto,
			@PathVariable("topicId") long topicId) {
		
		commentService.edit(commentDto, commentId, topicId);
		return ResponseEntity.status(HttpStatus.CREATED).body("Comment updated");
	}
	
	@RequestMapping(value = "{commentId}", method = RequestMethod.DELETE )
	public ResponseEntity<String> deleteComment(@PathVariable("commentId") long commentId,
			@PathVariable("topicId") long topicId) {
		
		commentService.delete(commentId, topicId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Comment deleated");
		
	}
	
}
