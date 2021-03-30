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
import com.omega.backend.forum.model.entity.User;
import com.omega.backend.forum.repository.UserRepository;
import com.omega.backend.forum.security.JwtProvider;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEnable(false);

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
        		.refreshToken("").expiresAt(Instant.now()
        				.plusMillis(jwtProvider.getJwtExpirationInMillis()))
        				.username(refrashToken.getUserName())
        				.build();
	}


}
