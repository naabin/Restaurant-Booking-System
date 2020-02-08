package com.reservation.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reservation.models.security.BookingUser;
import com.reservation.models.security.UserRole;
import com.reservation.repositories.BookingUserRepository;
import com.reservation.repositories.RoleRepository;
import com.reservation.securityconfig.PasswordEncrypt;
import com.reservation.services.BookingUserService;

@Service
public class BookingUserServiceImpl implements BookingUserService, UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(BookingUserServiceImpl.class);

	private final BookingUserRepository userRepo;

	private final RoleRepository roleRepo;
	
	private  PasswordEncrypt passwordEncrypt;

	public BookingUserServiceImpl(BookingUserRepository userRepo, 
			RoleRepository roleRepo, PasswordEncrypt passwordEncrypt) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncrypt = passwordEncrypt;
	}

	@Override
	@Transactional
	public BookingUser createUser(BookingUser user, Set<UserRole> userRoles) {
		BookingUser localUser = this.userRepo.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("User with username {} already exists. " + user.getUsername());
		} else {
			user.setPassword(this.passwordEncrypt.passwordEncoder().encode(user.getPassword()));
			for (UserRole role : userRoles) {
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

		if (user == null) {
			LOG.warn("User {} not found with username " + username);

			throw new UsernameNotFoundException(username + " not found");
		}

		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<BookingUser> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	public Optional<BookingUser> findUserById(Long id) {
		return userRepo.findById(id);
	}

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUserById(Long id) {
		userRepo.deleteById(id);
	}

}
