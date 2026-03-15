package com.substring.auth.auth_app_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.substring.auth.auth_app_backend.DTO.*;
import com.substring.auth.auth_app_backend.entities.*;
import com.substring.auth.auth_app_backend.security.JWTUtil;
import com.substring.auth.auth_app_backend.Repository.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody AuthRequest request){
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("Username already exists !");
		}
		User user=new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.ROLE_USER);
		
		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		
		User user=userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("Invalid username or password"));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}
		
		String token=jwtUtil.generateToken(user.getUsername());
		
		return ResponseEntity.ok(new AuthResponse(token));
	}
	

}
