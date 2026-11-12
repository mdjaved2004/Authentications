package com.authentication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authentication.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	
	boolean existsByName(String name);
	
	Optional<RoleEntity> findByName(String name);
}
