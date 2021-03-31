package com.omega.backend.forum.model.service;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omega.backend.forum.dto.UserDto;
import com.omega.backend.forum.dto.request.ChangePasswordRequest;
import com.omega.backend.forum.dto.request.EditEmailRequest;
import com.omega.backend.forum.dto.request.EditUserNameRequest;
import com.omega.backend.forum.dto.request.ForgotPasswordRequest;
import com.omega.backend.forum.dto.request.UploadFileRequest;
import com.omega.backend.forum.exception.BadRequestException;
import com.omega.backend.forum.exception.UnauthorizationException;
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.UserRepository;

import net.bytebuddy.utility.RandomString;



@Service
@Transactional
public class ProfileService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private MailService mailSrvice;
	
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
	
	public String handleForgotPassword(ForgotPasswordRequest forgotPassword) {
		
		User user = userRepository.findByUserName(forgotPassword.getUserName());
		
		if(user == null) 
			throw new BadRequestException("BAD CREDENTIALS!");
		
		if(!user.getEmail().equals(forgotPassword.getEmail())) 
			throw new BadRequestException("BAD CREDENTIALS!");
		
		String password = RandomString.make(10);
		
		mailSrvice.sendEmail(user.getEmail(), password);
		
		user.setPassword(passwordEncoder.encode(password));
		
		userRepository.save(user);
			
		return "NEW PASSWORD SENDED TO YOUR EMAIL!";
	}
	
	public String addAvatar(UploadFileRequest fileRequest) throws IOException {
		
		User user = userRepository.findByUserName(fileRequest.getUserName());
		
		if(!user.equals(authService.getCurrentUser()))
			throw new UnauthorizationException("YOU HAVE NO PERMISSION TO UPLOAD FILE");
		
		user.setAvatar(fileRequest.getFile().getBytes());
		
		userRepository.save(user);
		
		return "FILE UPLOADED";
		
	}
	
	
	
}
