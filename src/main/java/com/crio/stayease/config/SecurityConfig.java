package com.crio.stayease.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.crio.stayease.service.CustomUserDetailsService;
import com.crio.stayease.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService; 

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		//.csrf().disable().
		.authorizeHttpRequests(authorizeRequests -> 
			authorizeRequests
			.requestMatchers("/registration", "/login", "/authenticate", "/hello").permitAll()
			//.requestMatchers("/user/**").hasRole("USER")
			//.requestMatchers("/authenticate").permitAll()
			.anyRequest().authenticated())
//		.formLogin(formLogin ->
//        formLogin.disable()
//            //.loginPage("/login") // Custom login page, if you have one
//            //.permitAll() // Allow access to the login page
//    )
//    .logout(logout ->
//        logout.permitAll() // Allow access to logout functionality
//    )
    .csrf(csrf -> csrf.disable())
    .authenticationProvider(authenticationProvider())
    //.sessionManagement()
    //.sessionManagement(sessionManagement -> sessionManagement)
   // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
   // .and()
    .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//		.authorizeRequests()
//		.requestMatchers("/registration").permitAll()
//		.anyRequest()
//		.authenticated()
//		.and()
//		.httpBasic()
//		.and()
//		.csrf()
//		.disable();
		return http.build();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		//return new InMemoryUserDetailsManager(User.withUsername("user1").password(passwordEncoder().encode("password1")).roles("USER").build());
		return userDetailsService;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
