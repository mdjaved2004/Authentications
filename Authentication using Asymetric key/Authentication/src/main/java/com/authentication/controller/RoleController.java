package com.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.RoleRequestDto;
import com.authentication.service.RoleService;

import jakarta.validation.Valid;

@RestController
public class RoleController {
	
	private RoleService roleService;
	
	public RoleController(RoleService roleService) {
		super();
		this.roleService = roleService;
	}


	@PostMapping("/addRoles")
	public ResponseEntity<String> addRoles(@Valid @RequestBody RoleRequestDto requestDto){
		String message = roleService.addRoles(requestDto);
		return ResponseEntity.ok(message);
	}

}
