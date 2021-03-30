package com.omega.backend.forum.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.omega.backend.forum.model.entity.Topic;

@Repository
public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
	
}
