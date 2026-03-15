package com.substring.auth.auth_app_backend.Repository;

import java.util.Optional;	

import org.springframework.data.jpa.repository.JpaRepository;

import com.substring.auth.auth_app_backend.entities.*;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);

}
