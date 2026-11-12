package com.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class RoleRequestDto {
	@NotBlank
	@Size(min = 2, max = 100, message="Role name must be between 2 and 100 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Role name must contain only letters and digits")
	private String name;
}
