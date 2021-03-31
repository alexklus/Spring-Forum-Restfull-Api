package com.omega.backend.forum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.omega.backend.forum.model.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("SELECT u FROM User u where u.userName =:username")
	public User findByUserName(@Param("username")String userName);
	
	@Query("SELECT u FROM User u where u.email =:email")
	public User findByEmail(@Param("email")String email);

}
