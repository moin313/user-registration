package com.registration.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.registration.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public boolean existsByEmail(String email);

	public boolean existsByPhoneNumber(String phoneNumber);

	@Query(value = "SELECT new com.registration.entity.User(u.password, u.email, u.roles, u.userId) FROM User u WHERE u.email=:email")
	public Optional<User> findByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM User u WHERE lower(u.firstName) LIKE lower(concat('%', :searchBy, '%'))"
			+ "    OR lower(u.lastName) LIKE lower(concat('%', :searchBy, '%'))"
			+ "    OR lower(u.phoneNumber) LIKE lower(concat('%', :searchBy, '%'))"
			+ "    OR lower(u.email) LIKE lower(concat('%', :searchBy, '%'))")
	public Page<User> findAllByNameOrEmailOrContact(@Param("searchBy") String searchBy, Pageable pageable);

	List<User> findByPwdUpdatedOn(LocalDate date);
	
}