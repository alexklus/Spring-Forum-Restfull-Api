package com.omega.backend.forum.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.omega.backend.forum.dto.TopicDto;

import com.omega.backend.forum.dto.response.TopicPageResponse;
import com.omega.backend.forum.dto.response.TopicResponse;
import com.omega.backend.forum.exception.ForumException;
import com.omega.backend.forum.model.entity.Topic;
import com.omega.backend.forum.repository.TopicRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class TopicService {

	@Autowired
	private AuthService authService;

	@Autowired
	private TopicRepository topicRepository;

	public TopicResponse save(TopicDto topicDto) {

		Topic topic = topicRepository.save(mapToTopic(topicDto));

		return matToTopicResponse(topic);

	}

	public TopicPageResponse getPaginatedPosts(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

		Page<TopicResponse> posts = topicRepository.findAll(pageable).map(this::matToTopicResponse);

		return TopicPageResponse.builder()
				.content(posts.getContent())
				.size(posts.getSize()).page(posts.getNumber())
				.totalElements(posts.getNumberOfElements())
				.lastPage(posts.isLast()).build();

	}

	public void deleteById(long topicId) {

		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ForumException("Topic not exist!"));

		if (!authService.getCurrentUser().equals(topic.getUser())) {
			throw new ForumException("Topics can only be deleted by thier author!");
		}

		topicRepository.deleteById(topicId);
	}

	public TopicResponse getTopicById(Long topicId) {

		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(() -> new ForumException("Cannot find topic by user id:" + topicId));

		return matToTopicResponse(topic);
	}

	public TopicDto edit(Long id, TopicDto topicDto) {

		Topic topic = topicRepository.findById(id).get();

		if (!authService.getCurrentUser().equals(topic.getUser())) {
			throw new ForumException("Topics can only be edited by thier author!");
		}
		return topicDto;

	}

	// TODO An wrapper object for mapping
	private Topic mapToTopic(TopicDto topicDto) {
		return Topic.builder().id((long) 0).title(topicDto.getTitle()).user(authService.getCurrentUser()).build();
	}

	private TopicResponse matToTopicResponse(Topic topic) {
		return TopicResponse.builder().id(topic.getId()).topic(topic.getTitle()).author(topic.getUser().getUserName())
				.build();
	}

}
