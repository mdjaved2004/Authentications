package com.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.LoginRequestDto;
import com.authentication.dto.LoginResponseDto;
import com.authentication.service.JwtService;

import jakarta.validation.Valid;

@RestController
public class LoginController {
	
	private AuthenticationManager authenticationManager;
	
    private JwtService jwtService;
    
	public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public LoginResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
	    
		Authentication authenticationRequest= UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDto.getUsername(), loginRequestDto.getPassword());
		
		Authentication authentication =authenticationManager.authenticate(authenticationRequest);
		
		String token =jwtService.generateToken(authentication);
		
		return new LoginResponseDto(token);
		
	}
	
	@GetMapping("/hello")
	public ResponseEntity<String> register(Authentication authentication) {
	    return ResponseEntity.ok("Hello :" + authentication.getName()+"");
	}
}
