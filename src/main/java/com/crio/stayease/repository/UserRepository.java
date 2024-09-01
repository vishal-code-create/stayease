package com.crio.stayease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.stayease.entity.CustomUserDetails;

public interface UserRepository extends JpaRepository<CustomUserDetails, String> {
	
	public CustomUserDetails findByEmail(String username);

}