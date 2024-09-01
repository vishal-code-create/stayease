package com.crio.stayease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio.stayease.dto.UserLoginDetails;
import com.crio.stayease.dto.UserRegistrationDetails;
import com.crio.stayease.exception.UserNotFoundException;
import com.crio.stayease.service.RentReadService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RentReadController {

	@Autowired
	RentReadService rentReadService;

	@PostMapping("/registration")
	public ResponseEntity<String> registration(@ModelAttribute UserRegistrationDetails userRegistrationDetails) {
		// Add validation for user registration details
		// If some detail is invalid return bad_request
		log.info("inside registration method");
		boolean result = rentReadService.storeUserRegistrationDetails(userRegistrationDetails);
		if (result)
			return ResponseEntity.status(HttpStatus.CREATED).body("user registration successful");
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user registration not successful");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@ModelAttribute UserLoginDetails userLoginDetails) {
		log.debug("login details are" + userLoginDetails.toString());
		try {
			boolean authenticationResult = rentReadService.authenticateUser(userLoginDetails);
			if (!authenticationResult)
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password is invalid");
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		return ResponseEntity.ok("login successful");
	}
	
	@PostMapping("/hello1")
	public String hello() {
		return "hello";
	}

}
