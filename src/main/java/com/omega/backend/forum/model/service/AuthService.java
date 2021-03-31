package com.omega.backend.forum.model.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omega.backend.forum.dto.request.LoginRequest;
import com.omega.backend.forum.dto.request.RefreshTokenRequest;
import com.omega.backend.forum.dto.request.RegisterRequest;
import com.omega.backend.forum.dto.response.AuthenticationResponse;
import com.omega.backend.forum.exception.BadRequestException;
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.UserRepository;
import com.omega.backend.forum.security.JwtProvider;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest registerRequest) {
    	
    	User testUser = userRepository.findByUserName(registerRequest.getUsername());
		
		if(testUser != null) throw new BadRequestException("Invalid username!");
		
		testUser = userRepository.findByEmail(registerRequest.getEmail());
		
		if(testUser != null) throw new BadRequestException("Invalid email!");
        User user = User.builder().userId((long)0)
				.email(registerRequest.getEmail()).userName(registerRequest.getUsername())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role("ROLE_USER")
				.enable(true)
				.build();
        
        userRepository.save(user);

        
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(principal.getUsername());
                
    }

    
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        
        return AuthenticationResponse.builder().authenticationToken(token)
        		.refreshToken(refreshTokenService.generateRefreshToken().getToken())
        		.expiresAt(Instant.now()
        		.plusMillis(jwtProvider.getJwtExpirationInMillis()))
        		.username(loginRequest.getUsername())
        		.build();
    }

	public AuthenticationResponse refreshToken(RefreshTokenRequest refrashToken) {
		refreshTokenService.validateRefreshToken(refrashToken.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refrashToken.getUserName());
		
		return AuthenticationResponse.builder().authenticationToken(token)
        		.refreshToken(refrashToken.getRefreshToken()).expiresAt(Instant.now()
        				.plusMillis(jwtProvider.getJwtExpirationInMillis()))
        				.username(refrashToken.getUserName())
        				.build();
	}


}
