package com.authentication.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.authentication.dto.RegisterRequestDto;
import com.authentication.dto.RegisterResponseDto;
import com.authentication.entity.RoleEntity;
import com.authentication.entity.UserEntity;
import com.authentication.exception.ResourceAlreadyExistsException;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
   

    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}


    @Transactional
	public RegisterResponseDto register(RegisterRequestDto requestDto) {
    	 
    		// Check whether email already exists
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "You are already registered. Please login."
            );
        }
        
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new ResourceAlreadyExistsException("Please enter a strong username using letters, digits, and special characters (e.g., @ or #)");
        }
        
        // Get Role in dataBase
        RoleEntity role = roleRepository.findByName("USER")
                .orElseThrow(() -> {
                    System.out.println("ERROR: USER role not found in database");
                    return new RuntimeException("USER role not found");
                });
        
        //Create UserEntity object
        UserEntity user = new UserEntity();
        	
        user.getRoles().add(role);    

        //Set Data In UserEntity
        user.setName(requestDto.getName());
        
        //Convert the username to lowercase
        user.setUsername(requestDto.getUsername().toLowerCase());
        user.setEmail(requestDto.getEmail());
        user.setEnable(true);

        //Hash password using BCrypt
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        //Save user in database
        UserEntity savedUser = userRepository.save(user);

        //Create response DTO
        RegisterResponseDto responseDto =new RegisterResponseDto();

        responseDto.setUsername(savedUser.getUsername());
        responseDto.setMessage("You are successfully registered");

        return responseDto;
    }
}