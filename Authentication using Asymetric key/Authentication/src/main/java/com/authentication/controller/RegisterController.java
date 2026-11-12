package com.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.RegisterRequestDto;
import com.authentication.dto.RegisterResponseDto;
import com.authentication.service.RegisterService;

import jakarta.validation.Valid;


@RestController
public class RegisterController {
	
	private RegisterService registerService;

	public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto userRegisterRequestDto){
		
		RegisterResponseDto register = registerService.register(userRegisterRequestDto);
		
		return ResponseEntity.ok().body(register);
	}
	
}
