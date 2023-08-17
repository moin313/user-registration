package com.registration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.registration.entity.User;
import com.registration.payload.UtilResponse;
import com.registration.payload.request.SearchRequest;
import com.registration.payload.request.UserRequest;
import com.registration.service.UserService;
import com.registration.util.Constants;
import com.registration.util.ResponseMsg;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@Tag(description = Constants.USER_TAG_DESCRIPTION, name = "User Resource")
@SecurityRequirement(name = "javainuseapi")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Operation(summary = "Register a new user.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ResponseMsg.CREATED, content = @Content),
			@ApiResponse(responseCode = "400", description = ResponseMsg.EMAIL_FOUND + "/"
					+ ResponseMsg.CONTACT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = ResponseMsg.INTERNAL_ERROR, content = @Content) })
	@PreAuthorize("hasAnyAuthority('ADMIN', 'APPRAISER')")
	@PostMapping("/register-user")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<?> registerUser(@Validated @RequestBody UserRequest request, @RequestHeader("Authorization") String tokenHeader) {
		logger.info("Request to register user");
		return userService.registerUser(request, tokenHeader);
	}

	
	
	@Operation(summary = "Get a user details by its id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ResponseMsg.FETCHED, content = @Content),
			@ApiResponse(responseCode = "404", description = ResponseMsg.NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = ResponseMsg.INTERNAL_ERROR, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }), })
	@GetMapping("/get")
	@PreAuthorize("hasAnyAuthority('APPRAISER', 'ADMIN', 'PO')")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<?> getUserDetails(@RequestParam(name = "userId", required = true) long userId,
			@RequestHeader("Authorization") String tokenHeader) {
		logger.info("Request to fetch user with user id : " + userId);
		System.out.println(tokenHeader);
		return userService.fetchUserDetails(userId, tokenHeader);
	}

	
	@Operation(summary = "Update a single user by its id.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ResponseMsg.UPDATED, content = @Content),
			@ApiResponse(responseCode = "404", description = ResponseMsg.NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = ResponseMsg.INTERNAL_ERROR, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }), })
	@PutMapping("/update-user")
	@PreAuthorize("hasAnyAuthority('APPRAISER', 'ADMIN', 'PO')")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<?> updateUserDetails(
			@Valid @RequestBody UserRequest request, @RequestParam(name = "userId", required = true) long userId,
			@RequestHeader("Authorization") String tokenHeader) {
		logger.info("Request to update user with userId {}", userId);
		return userService.updateUserDetails(request, userId, tokenHeader);
	}

	
	@Operation(summary = "Delete an existing user.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ResponseMsg.DELETED, content = @Content),
			@ApiResponse(responseCode = "404", description = ResponseMsg.NOT_FOUND, content = @Content),
			@ApiResponse(responseCode = "500", description = ResponseMsg.INTERNAL_ERROR, content = @Content) })
	@DeleteMapping("/delete-user")
	@PreAuthorize("hasAnyAuthority('APPRAISER', 'ADMIN')")
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<?> deleteUserDetails(@RequestParam(name = "userId", required = true) long userId,
			@RequestHeader("Authorization") String tokenHeader) {
		logger.info("Request to delete user with userId {}", userId);
		return userService.deleteUser(userId, tokenHeader);
	}
	
	
	@Operation(summary = "Pagination/Searching/Sorting for all user.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = ResponseMsg.FETCHED, content = @Content),
			@ApiResponse(responseCode = "404", description = ResponseMsg.INCORRECT_PARAMETER, content = @Content),
			@ApiResponse(responseCode = "500", description = ResponseMsg.INTERNAL_ERROR, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UtilResponse.class)) }), })
	@GetMapping("/search")
	@PermitAll
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<?> getAll(@Valid @RequestBody SearchRequest searchRequest) {
		logger.info("Request to fetch all user with pagination.");
		return userService.getAll(searchRequest);
	}
}
