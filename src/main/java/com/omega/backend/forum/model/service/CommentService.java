package com.omega.backend.forum.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omega.backend.forum.dto.CommentDto;
import com.omega.backend.forum.model.entity.Comment;
import com.omega.backend.forum.model.entity.Topic;
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.CommentRepository;
import com.omega.backend.forum.repository.TopicRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	private AuthService authService;
	
	private TopicRepository topicRepository;
	
	public void save(CommentDto commentDto) {
		System.out.println(commentDto.getTitle());
	}
	
	
	private Comment mapToComment(CommentDto commentDto, User user, Topic topic) {
		return Comment.builder().id((long)0).text(commentDto.getContent())
				.user(user).topic(topic).build();
	}
}
