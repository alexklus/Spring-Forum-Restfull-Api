package com.omega.backend.forum.repository;

import org.springframework.data.repository.CrudRepository;

import com.omega.backend.forum.model.entity.Like;

public interface LikeRepository extends CrudRepository<Like, Long> {

}
