package com.reservation.services.impl;

import java.util.ArrayList;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reservation.models.security.BookingUser;
import com.reservation.models.security.UserRole;
import com.reservation.repositories.BookingUserRepository;
import com.reservation.repositories.RoleRepository;
import com.reservation.services.BookingUserService;

@Service
public class BookingUserServiceImpl implements BookingUserService, UserDetailsService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BookingUserServiceImpl.class);
	
	private final BookingUserRepository userRepo;
	
	private final RoleRepository roleRepo;
	
	public BookingUserServiceImpl(BookingUserRepository userRepo, RoleRepository roleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}




	@Override
	@Transactional
	public BookingUser createUser(BookingUser user, Set<UserRole> userRoles) {
		BookingUser localUser = this.userRepo.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("User with username {} already exists. " + user.getUsername());
		}
		else {
			for(UserRole role: userRoles) {
				roleRepo.save(role.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			localUser = userRepo.save(user);
			
			
		}
		return localUser;
	}




	@Override
	public UserDetails loadUserByUsername(String username) {
		
		BookingUser user = userRepo.findByUsername(username);
		
		if(user == null) {
			LOG.warn("User {} not found with username " + username);
			
			throw new UsernameNotFoundException(username + " not found");
		}
		
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

}
