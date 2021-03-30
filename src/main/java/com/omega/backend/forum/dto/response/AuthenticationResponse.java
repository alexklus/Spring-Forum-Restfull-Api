package com.omega.backend.forum.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
	
	private String authenticationToken;
	
	private String username;
	
	private Instant expiresAt;
	
	private String refreshToken;
	
}
