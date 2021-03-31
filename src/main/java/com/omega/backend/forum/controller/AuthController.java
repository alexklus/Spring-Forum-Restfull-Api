package com.omega.backend.forum.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omega.backend.forum.dto.request.ForgotPasswordRequest;
import com.omega.backend.forum.dto.request.LoginRequest;
import com.omega.backend.forum.dto.request.RefreshTokenRequest;
import com.omega.backend.forum.dto.request.RegisterRequest;
import com.omega.backend.forum.dto.response.AuthenticationResponse;
import com.omega.backend.forum.model.service.AuthService;
import com.omega.backend.forum.model.service.ProfileService;
import com.omega.backend.forum.model.service.RefreshTokenService;

import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
    
	private final RefreshTokenService refreshTokenService;
	
	private final ProfileService profileService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        
        return new ResponseEntity<String>("User Registration Successful",HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    
    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refrashToken) {
    	return authService.refreshToken(refrashToken);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
    @PostMapping("/resetpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
    	
    	return ResponseEntity.status(HttpStatus.OK).body(profileService.handleForgotPassword(forgotPasswordRequest));
    }
    
}
