package com.crio.stayease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.stayease.dto.AuthenticationRequest;
import com.crio.stayease.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
//@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
		log.info("inside authenticate method "+request);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		log.info("inside authenticate method1");
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		log.info("inside authenticate method2");
		if(userDetails != null) {
			return ResponseEntity.ok(jwtUtil.generateToken(request.getEmail()));
		}
		log.info("inside authenticate method3");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user details not found for email "+request.getEmail());
	}

}
