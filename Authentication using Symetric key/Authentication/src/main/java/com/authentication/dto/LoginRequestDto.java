package com.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginRequestDto {
	
	@NotBlank(message = "Username is required")
	@Size(min = 5, max = 30, message = "Username must be between 5 and 20 characters")
	private String username;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, max = 30, message = "Password must be between 6 and 25 characters")
	private String password;
	

}
