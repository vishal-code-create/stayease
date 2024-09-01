package com.crio.stayease.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	private String secretKey = "hello";
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();
	}
	
	public Claims extractClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}
	
	public boolean validateToken(String token, String username) {
		return (username.equals(extractUsername(token)) && !isTokenExpired(token));
	}

}
