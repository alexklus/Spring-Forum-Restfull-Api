package com.omega.backend.forum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.omega.backend.forum.model.entity.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query("from Comment as c where c.topic.id =:topicId")
	public Page<Comment> finaPagebleByTopicId(@Param("topicId")Long topicId, Pageable pageable);
}
