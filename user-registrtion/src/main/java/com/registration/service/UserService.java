package com.registration.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.registration.entity.User;
import com.registration.payload.request.SearchRequest;
import com.registration.payload.request.UserRequest;

import jakarta.validation.Valid;

@Service
public interface UserService {
	
	public ResponseEntity<?> registerUser(UserRequest request, String tokenHeader);

	public ResponseEntity<?> fetchUserDetails(long userId, String tokenHeader);

	public ResponseEntity<?> updateUserDetails(@Valid UserRequest request, long userId, String tokenHeader);

	public ResponseEntity<?> deleteUser(long userId, String tokenHeader);
	
	public ResponseEntity<?> getAll(SearchRequest request);
	
	public List<User> getUsersWithPwdUpdatedOn(LocalDate date);
}
