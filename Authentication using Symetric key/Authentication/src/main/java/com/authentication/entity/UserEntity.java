package com.authentication.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, unique = true, length = 254)
	private String email;

	@Column(nullable = false, unique = true, length = 30)
	private String username;

	@Column(nullable = false, length = 60)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name="user_roles",
			joinColumns=@JoinColumn(name="user_id"),
			inverseJoinColumns=@JoinColumn(name="role_id")	
		)
	private Set<RoleEntity> roles= new HashSet<>();
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean enable = true;
}
