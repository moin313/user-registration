package com.registration.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.registration.advice.DuplicateFieldException;
import com.registration.advice.EntityNotFoundException;
import com.registration.advice.UtilException;
import com.registration.entity.User;
import com.registration.payload.UtilResponse;
import com.registration.payload.request.SearchRequest;
import com.registration.payload.request.UserRequest;
import com.registration.repository.UserRepository;
import com.registration.security.jwt.JwtUtil;
import com.registration.service.UserService;
import com.registration.util.ResponseMsg;

import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<?> registerUser(UserRequest request, String tokenHeader) {

		List<String> extractUserRoles = jwtUtil.extractUserRoles(tokenHeader.substring(7));
		for (String role : extractUserRoles) {
			System.out.println("ROLE :  " + role + " REQUEST : " + request.getRoles());
			if ((role.equals("APPRAISER") && request.getRoles().contains("PO")) || (role.equals("ADMIN")
					&& (request.getRoles().contains("APPRAISER") || request.getRoles().contains("ADMIN")))) {
				break;
			} else {
				throw new AccessDeniedException(ResponseMsg.FORBIDDEN_ERROR);
			}
		}

		User user = modelMapper.map(request, User.class);
		if (userRepo.existsByEmail(user.getEmail())) {
			logger.error("User already exist with email address :" + user.getEmail());
			throw new DuplicateFieldException(ResponseMsg.EMAIL_FOUND);
		} else if (userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
			logger.error("User already exist with contact number :" + user.getPhoneNumber());
			throw new DuplicateFieldException(ResponseMsg.CONTACT_FOUND);
		}
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		try {
			userRepo.save(user);
			logger.debug("User successfully registered with email : " + user.getEmail());
			return ResponseEntity.ok().body(new UtilResponse(null, ResponseMsg.CREATED, HttpStatus.OK));
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new UtilException(ResponseMsg.INTERNAL_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> fetchUserDetails(long userId, String tokenHeader) {
		if (jwtUtil.getUserIdFromToken(tokenHeader.substring(7)) != userId) {
			throw new AccessDeniedException(ResponseMsg.FORBIDDEN_ERROR);
		}
		User user = userRepo.findById(userId).orElseThrow(() -> {
			logger.debug("User not exists with user id : " + userId);
			throw new EntityNotFoundException(ResponseMsg.USER_NOT_FOUND);

		});
		logger.debug("User successfully fetched for user id : " + userId);
		return ResponseEntity.ok().body(new UtilResponse(user, ResponseMsg.FETCHED, HttpStatus.OK));
	}

	@Override
	public ResponseEntity<?> updateUserDetails(@Valid UserRequest userRequest, long userId, String tokenHeader) {
		if (jwtUtil.getUserIdFromToken(tokenHeader.substring(7)) != userId) {
			throw new AccessDeniedException(ResponseMsg.FORBIDDEN_ERROR);
		}
		User user = userRepo.findById(userId).orElseThrow(() -> {
			logger.debug("User not exists with user id : " + userId);
			throw new EntityNotFoundException(ResponseMsg.USER_NOT_FOUND);

		});

		int id = user.getUserId();
		LocalDate dateOfCreation = user.getDateOfCreation();
		LocalDate dateOfUpdation = user.getDateOfUpdate();
		user = modelMapper.map(userRequest, User.class);
		if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
			user.setPwdUpdatedOn(LocalDate.now());
		} else {
			user.setDateOfUpdate(dateOfUpdation);
		}
		user.setUserId(id);
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setDateOfCreation(dateOfCreation);

		try {
			user = userRepo.save(user);
			logger.debug("User successfully Updated for user id : " + userId);
			return ResponseEntity.ok().body(new UtilResponse(user, ResponseMsg.UPDATED, HttpStatus.OK));
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new UtilException(ResponseMsg.INTERNAL_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteUser(long userId, String tokenHeader) {
		List<String> extractUserRoles = jwtUtil.extractUserRoles(tokenHeader.substring(7));
		for (String role : extractUserRoles) {
			if (role.equals("APPRAISER")) {
				break;
			} else if (role.equals("PO") || role.equals("ADMIN")) {
				throw new AccessDeniedException(ResponseMsg.FORBIDDEN_ERROR);
			}
		}
		if (!userRepo.existsById(userId)) {
			logger.debug("User not exists with user id : " + userId);
			throw new EntityNotFoundException(ResponseMsg.USER_NOT_FOUND);
		}
		try {
			userRepo.deleteById(userId);
			logger.debug("User successfully deleted for user id : " + userId);
			return ResponseEntity.ok().body(new UtilResponse(null, ResponseMsg.DELETED, HttpStatus.OK));
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			throw new UtilException(ResponseMsg.INTERNAL_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAll(SearchRequest searchRequest) {
		logger.debug("Searching all user");
		String sortBy = searchRequest.getSortBy();
		sortBy = (null != sortBy) ? sortBy : "Id";
		String order = (null == searchRequest.getOrder() || searchRequest.getOrder().equals("")) ? "ASC"
				: searchRequest.getOrder();
		Pageable pageable = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getSize(),
				(order.equals("ASC") || order.equals("asc")) ? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending());
		Page<User> pageFromRepo = null;
		String searchBy = (null == searchRequest.getSearchBy()) ? "" : searchRequest.getSearchBy();
		try {
			pageFromRepo = userRepo.findAllByNameOrEmailOrContact(searchBy, pageable);
			logger.debug("List of users fetched successfully.");
			return ResponseEntity.ok().body(new UtilResponse(pageFromRepo, ResponseMsg.FETCHED, HttpStatus.FOUND));
		} catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
			return ResponseEntity.internalServerError()
					.body(new UtilResponse(null, ResponseMsg.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}

	public List<User> getUsersWithPwdUpdatedOn(LocalDate date) {
		return userRepo.findByPwdUpdatedOn(date);
	}
}
