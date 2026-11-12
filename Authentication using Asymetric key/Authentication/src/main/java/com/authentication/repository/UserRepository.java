package com.authentication.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.authentication.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	 
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	Optional<UserEntity> findByUsername(String username);
}
