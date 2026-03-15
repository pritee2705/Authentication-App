package com.substring.auth.auth_app_backend.security;

import java.security.*;
import java.util.Date;


import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component
public class JWTUtil {
	
	private final String SECRET="mysecretkeymysecretkeymysecretkey123";
	
	private final Key key=Keys.hmacShaKeyFor(SECRET.getBytes());
	
	public String generateToken(String username) {
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+86400000))
				.signWith(key)
				.compact();
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build().parseClaimsJws(token).getBody().getSubject();
	}

}
