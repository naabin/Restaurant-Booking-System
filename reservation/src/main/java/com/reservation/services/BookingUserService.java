package com.reservation.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.reservation.models.security.BookingUser;
import com.reservation.models.security.UserRole;

public interface BookingUserService {
	
	
	BookingUser createUser(BookingUser user, Set<UserRole> userRoles);
	
	List<BookingUser> getAllUsers();
	
	Optional<BookingUser> findUserById(Long id);
	
	void deleteUserById(Long id);
	
	UserDetails loadUserByUsername(String username);

}
