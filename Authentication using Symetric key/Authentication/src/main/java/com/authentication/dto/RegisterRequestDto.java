package com.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {
	
	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Name must contain only letters and spaces")
	private String name;
	
	@NotBlank(message = "Username is required")
	@Size(min = 5, max = 30, message = "Username must be between 5 and 20 characters")
	@Pattern(regexp = "^[a-zA-Z0-9@#]+$", message = "Username must contain only letters, digits, @ or #")
	private String username;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Please enter a valid email address")
	@Size(min = 5, max = 254, message = "Username must be between 5 and 254 characters")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 6, max = 64, message = "Password must be between 6 and 25 characters")
	@Pattern(regexp = "^[a-zA-Z0-9@#]+$", message = "Password must contain only letters, digits, @ or #")
	private String password;
}
