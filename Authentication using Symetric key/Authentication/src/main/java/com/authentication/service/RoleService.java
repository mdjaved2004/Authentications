package com.authentication.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authentication.dto.RoleRequestDto;
import com.authentication.entity.RoleEntity;
import com.authentication.exception.ResourceAlreadyExistsException;
import com.authentication.repository.RoleRepository;

// create logic update, 
@Service
public class RoleService {
	
	private RoleRepository repository;
	
	public RoleService(RoleRepository repository) {
		this.repository = repository;
	}
 
	@Transactional
	public String addRoles(RoleRequestDto requestDto) {
		
		String roleName=requestDto.getName().toUpperCase();
		
		if (repository.existsByName(roleName)) {
			throw new ResourceAlreadyExistsException("Role already exists : " + roleName);
		 }
		
		RoleEntity roleEntity= new RoleEntity();
		 
		roleEntity.setName(roleName);
		
		RoleEntity saveRole = repository.save(roleEntity);
				
		return "Succesfully addes role : "+saveRole.getName()+ "";
	}
	
}
