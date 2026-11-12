package com.authentication.security;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.authentication.entity.UserEntity;

public class CustomUserDetails implements UserDetails{
	
	private UserEntity userEntity;
	
	public CustomUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	
	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}
	
	@Override
	public @Nullable String getPassword() {
		return userEntity.getPassword();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userEntity.getRoles().stream()
				.map(
						role -> new SimpleGrantedAuthority(role.getName()
					)
				).toList();
	}
	
	@Override
	public boolean isEnabled() {
		return userEntity.isEnable();
	}

	

}
