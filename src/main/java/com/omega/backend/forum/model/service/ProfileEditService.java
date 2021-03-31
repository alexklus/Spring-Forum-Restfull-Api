package com.omega.backend.forum.model.service;

import javax.servlet.UnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omega.backend.forum.dto.UserDto;
import com.omega.backend.forum.dto.request.ChangePasswordRequest;
import com.omega.backend.forum.dto.request.EditEmailRequest;
import com.omega.backend.forum.dto.request.EditUserNameRequest;
import com.omega.backend.forum.exception.BadRequestException;
import com.omega.backend.forum.exception.UnauthorizationException;
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.UserRepository;



@Service
@Transactional
public class ProfileEditService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	public UserDto updateUsername(EditUserNameRequest editUserNameRequest) {
		
		User user = userRepository.findByUserName(editUserNameRequest.getCurrentUserName());
		
		if(user == null ) 
			throw new BadRequestException("INVALID USER" + editUserNameRequest.getCurrentUserName());
		
		User testUserName = userRepository.findByUserName(editUserNameRequest.getNewUserName());
		
		if(testUserName!=null) 
			throw new BadRequestException("INVALID NEW USER NAME " + editUserNameRequest.getNewUserName());
		
		if(!user.equals(authService.getCurrentUser())) 
			throw new UnauthorizationException("YOU HAVE NO PERMITTION TO CHANGE THIS USER!");
		
		user.setUserName(editUserNameRequest.getNewUserName());
		
		userRepository.save(user);
		
		return UserDto.builder()
				.id(user.getUserId()).userName(user.getUserName())
				.email(user.getEmail()).build();
	}
	
	public UserDto updateEmail(EditEmailRequest editEmailRequest) {
		
		User user = userRepository.findByEmail(editEmailRequest.getCurrentEmail());
		
		if(user == null ) 
			throw new BadRequestException("INVALID USER" + editEmailRequest.getCurrentEmail());
		
		User testUserName = userRepository.findByUserName(editEmailRequest.getNewEmail());
		
		if(testUserName!=null) 
			throw new BadRequestException("INVALID NEW USER NAME " + editEmailRequest.getNewEmail());
		
		
		if(!user.equals(authService.getCurrentUser())) 
			throw new UnauthorizationException("YOU HAVE NO PERMITTION TO CHANGE THIS USER!");
		
		user.setEmail(editEmailRequest.getNewEmail());
		
		userRepository.save(user);
		
		return UserDto.builder()
				.id(user.getUserId()).userName(user.getUserName())
				.email(user.getEmail()).build();
		
	}
	
	public void changePassword(ChangePasswordRequest changePasswordRequest) {
		
		User user = userRepository.findByUserName(changePasswordRequest.getUserName());
		
		if(!user.equals(authService.getCurrentUser())) 
			throw new UnauthorizationException("YOU HAVE NO PERMITTION TO CHANGE THIS USER!");
		
		user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		
		
		userRepository.save(user);
		
	}
	
}
