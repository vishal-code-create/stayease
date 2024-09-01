package com.crio.stayease.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.stayease.config.SecurityConfig;
import com.crio.stayease.dto.UserLoginDetails;
import com.crio.stayease.dto.UserRegistrationDetails;
import com.crio.stayease.entity.CustomUserDetails;
import com.crio.stayease.exception.UserNotFoundException;
import com.crio.stayease.repository.UserRepository;

@Service
public class RentReadService {

	@Autowired
	UserRepository rentReadRepository;

	@Autowired
	SecurityConfig securityConfig;

	public boolean storeUserRegistrationDetails(UserRegistrationDetails userRegistrationDetails) {
		String encodedPassword = securityConfig.passwordEncoder().encode(userRegistrationDetails.getPassword());
		CustomUserDetails userDetails = new CustomUserDetails(userRegistrationDetails.getFirstName(),
				userRegistrationDetails.getLastName(), userRegistrationDetails.getEmail(), encodedPassword);
		CustomUserDetails userDetailsObj = rentReadRepository.save(userDetails);
		if (userDetailsObj != null)
			return true;
		else
			return false;
	}

	public boolean authenticateUser(UserLoginDetails userLoginDetails) throws UserNotFoundException {
		Optional<CustomUserDetails> optionalUserDetails = rentReadRepository.findById(userLoginDetails.getEmail());
		if (optionalUserDetails.isEmpty())
			throw new UserNotFoundException("no user present with email " + userLoginDetails.getEmail());

		return securityConfig.passwordEncoder().matches(userLoginDetails.getPassword(),
				optionalUserDetails.get().getPassword());
	}

}
