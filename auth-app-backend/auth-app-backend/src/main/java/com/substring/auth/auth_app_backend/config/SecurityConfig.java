package com.substring.auth.auth_app_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.substring.auth.auth_app_backend.security.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JWTFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf-> csrf.disable())
		.authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**").permitAll()  // login/register is public
	            .requestMatchers("/api/tasks/**").authenticated()  // all task endpoints need JWT
	            .anyRequest().authenticated()
	        )
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
