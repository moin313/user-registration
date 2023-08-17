package com.registration.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.advice.EntityNotFoundException;
import com.registration.payload.UtilResponse;
import com.registration.payload.request.LoginRequest;
import com.registration.security.SecurityUser;
import com.registration.security.jwt.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = jwtUtil.generateJwtToken(authentication);

			SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());
			logger.debug("Jwt token generated successfully for {}", loginRequest.getEmail());
			return ResponseEntity.ok()
					.body(new UtilResponse(jwtToken, userDetails.getUsername() + " : " + roles, HttpStatus.OK));
		} catch (Exception e) {
			logger.info("Exception for generating token for {} {}", loginRequest.getEmail(), e.getMessage());
			throw new EntityNotFoundException("Email address or password is incorrect.");
		}
	}
}
