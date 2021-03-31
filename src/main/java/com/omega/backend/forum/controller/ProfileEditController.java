package com.omega.backend.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.omega.backend.forum.dto.UserDto;
import com.omega.backend.forum.dto.request.ChangePasswordRequest;
import com.omega.backend.forum.dto.request.EditEmailRequest;
import com.omega.backend.forum.dto.request.EditUserNameRequest;
import com.omega.backend.forum.model.service.ProfileEditService;


@RestController
@RequestMapping(value = "api/editprofile")
public class ProfileEditController {
	
	@Autowired
	private ProfileEditService profileService;
	
	@RequestMapping(value = "username")
	public ResponseEntity<UserDto> updateUserName(@RequestBody EditUserNameRequest editUserNameRequest) {
		
		return ResponseEntity.status(HttpStatus.OK).body(profileService.updateUsername(editUserNameRequest));
	}
	
	@RequestMapping(value = "email", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> updateEmail(@RequestBody EditEmailRequest editEmailRequest) {
		
		return ResponseEntity.status(HttpStatus.OK).body(profileService.updateEmail(editEmailRequest));
	}
	
	@RequestMapping(value = "password", method = RequestMethod.PUT)
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		
		profileService.changePassword(changePasswordRequest);
		
		return ResponseEntity.status(HttpStatus.OK).body("PASSWORD CHANGED!");
	}

}
