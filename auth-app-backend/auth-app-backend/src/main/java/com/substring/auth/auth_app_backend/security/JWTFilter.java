package com.substring.auth.auth_app_backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//import com.example.TaskManager.Services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	//private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return request.getServletPath().startsWith("/api/auth");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
														 HttpServletResponse response,
														 FilterChain filterChain)
			throws ServletException, IOException {
		
		String path = request.getServletPath();

	    if (path.startsWith("/api/auth")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
		
		String authHeader=request.getHeader("Authorization");
		
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			String token=authHeader.substring(7);
			String username=jwtUtil.extractUsername(token);
			
			UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken auth=
					new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
		// TODO Auto-generated method stub
		
	}
	

}
