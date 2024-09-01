package com.crio.stayease.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.stayease.repository.UserRepository;

//@Service
public class AuthServiceImpl {
	
	//@Autowired
	PasswordEncoder passwordEncoder;
	
	//@Autowired
	UserRepository userRepository;
	
	//@Autowired
	AuthenticationManager authenticationManager;
	
	//public AuthResponse register(Register)

}
