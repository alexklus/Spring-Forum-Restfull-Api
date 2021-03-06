package com.omega.backend.forum.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omega.backend.forum.dto.CommentDto;
import com.omega.backend.forum.dto.response.CommentResponse;
import com.omega.backend.forum.dto.response.LikeResponse;
import com.omega.backend.forum.exception.BadRequestException;
import com.omega.backend.forum.exception.UnauthorizationException;
import com.omega.backend.forum.model.entity.Comment;
import com.omega.backend.forum.model.entity.Like;
import com.omega.backend.forum.model.entity.Topic;
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.CommentRepository;
import com.omega.backend.forum.repository.LikeRepository;
import com.omega.backend.forum.repository.TopicRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

	private final CommentRepository commentRepository;

	private AuthService authService;

	private TopicRepository topicRepository;
	
	private LikeRepository likeRepository;

	public CommentResponse save(CommentDto commentDto, Long topicId) {

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new BadRequestException("Topic not exists " + topicId));

		Comment comment = mapToComment(commentDto, authService.getCurrentUser(), topic);

		return mapToCommentResponse(commentRepository.save(comment));

	}
	
	public CommentResponse getComment(long commentId, long topicId) {
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new BadRequestException("Comment Not exists!" + commentId));

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new BadRequestException("Topic not exist!" + topicId));

		if (!comment.getTopic().getId().equals(topic.getId()))
			throw new BadRequestException("Comment " + commentId + "Not releted to Topic " + topicId);
		
		return mapToCommentResponse(comment);
	}

	public Page<CommentResponse> getComments(long topicId, int page, int size) {

		Pageable pageable = PageRequest.of(page - 1, size);
		System.out.println(topicId);

		Page<Comment> comments = commentRepository.finaPagebleByTopicId(topicId, pageable);

		return comments.map(this::mapToCommentResponse);

	}

	public CommentResponse edit(CommentDto commentDto, long commentId, long topicId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new BadRequestException("Comment Not exists!" + commentId));

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new BadRequestException("Topic not exist!" + topicId));

		if (!comment.getTopic().getId().equals(topic.getId()))
			throw new BadRequestException("Comment " + commentId + "Not releted to Topic " + topicId);

		if (comment.getUser().equals(authService.getCurrentUser())) {

			comment.setText(commentDto.getContent());
			return mapToCommentResponse(commentRepository.save(comment));
		
		}

		throw new UnauthorizationException("You don't have permission to update this comment!");
	}

	public void delete(long commentId, long topicId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new BadRequestException("Comment Not exists!" + commentId));

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new BadRequestException("Topic not exist!" + topicId));

		if (!comment.getTopic().getId().equals(topic.getId()))
			throw new BadRequestException("Comment " + commentId + "Not releted to Topic " + topicId);

		if (!comment.getUser().equals(authService.getCurrentUser())) {
			throw new UnauthorizationException("You don't have permission to update this comment!");
		}

		commentRepository.delete(comment);
	}
	
	public LikeResponse like(long commentId, long topicId) {
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new BadRequestException("Comment Not exists!" + commentId));

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new BadRequestException("Topic not exist!" + topicId));

		if (!comment.getTopic().getId().equals(topic.getId()))
			throw new BadRequestException("Comment " + commentId + "Not releted to Topic " + topicId);
		
		User currentUser = authService.getCurrentUser();
		
		comment.getLikes().forEach((like)->{
			if(currentUser.equals(like.getUser())) {
				throw new BadRequestException("You have all ready liked this comment!");
			}
		});
		Like like = Like.builder().comment(comment).user(currentUser).build();
		
//		comment.addLike(like);
		
		likeRepository.save(like);
		
		
		return mapToLikeResponse(like);
		
	}
	
	public String dislike(long commentId, long topicId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new BadRequestException("Comment Not exists!" + commentId));

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new BadRequestException("Topic not exist!" + topicId));
		
		
		if (!comment.getTopic().getId().equals(topic.getId()))
			throw new BadRequestException("Comment " + commentId + "Not releted to Topic " + topicId);
		
		User currentUser = authService.getCurrentUser();
		

		for (Like like : comment.getLikes()) {

			if (like.getUser().equals(currentUser)) {
				comment.getLikes().remove(like);
				likeRepository.delete(like);
				return "dislike succeeded";
			}
		}
		
		throw new BadRequestException("Yous did not liked commnet yet");
		
	}
	
	//###################### TODO ADD WRAPER CLASS FOR MAPPING ENTITY TO DTO #########################

	private Comment mapToComment(CommentDto commentDto, User user, Topic topic) {
		return Comment.builder().id((long) 0).text(commentDto.getContent()).user(user).topic(topic).build();
	}

	private CommentResponse mapToCommentResponse(Comment comment) {
		return CommentResponse.builder().id(comment.getId()).content(comment.getText())
				.author(comment.getUser().getUserName())
				.topicId(comment.getTopic().getId()).likes(comment.getLikes().size())
				.build();
	}
	private LikeResponse mapToLikeResponse(Like like) {
		return LikeResponse.builder().commentId(like.getComment()
						.getId()).userName(like.getUser().getUserName()).build();
	}

}
