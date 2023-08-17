package com.registration.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.registration.repository.UserRepository;
import com.registration.security.SecurityUser;
import com.registration.util.ResponseMsg;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepo;

	public CustomUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		SecurityUser loadUser = userRepo.findByEmail(email).map(SecurityUser::new)
				.orElseThrow(() -> new UsernameNotFoundException(ResponseMsg.NOT_FOUND));
		logger.info("Current loaded user : " + loadUser.getUsername() + " " + loadUser.getAuthorities());
		return loadUser;
	}
}
