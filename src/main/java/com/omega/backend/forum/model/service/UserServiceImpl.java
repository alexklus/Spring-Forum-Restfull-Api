package com.omega.backend.forum.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.omega.backend.forum.dto.UserDto;
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void addUser(UserDto user) {
		
		User userToAdd = new User();
		userToAdd.setUserId((long)0);
		userToAdd.setEmail(user.getEmail());
		userToAdd.setUserName(user.getUsername());
		userToAdd.setPassword(passwordEncoder.encode(user.getPassword()));
		userToAdd.setRole("ROLE_USER");
		userToAdd.setEnable(true);
		
		userRepository.save(userToAdd);
	}

}
