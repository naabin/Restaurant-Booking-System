package com.reservation.services;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.reservation.models.security.BookingUser;
import com.reservation.models.security.UserRole;

public interface BookingUserService {
	
	
	BookingUser createUser(BookingUser user, Set<UserRole> userRoles);
	
	UserDetails loadUserByUsername(String username);

}
