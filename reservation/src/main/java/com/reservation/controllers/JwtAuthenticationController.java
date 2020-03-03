package com.reservation.controllers;


import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.models.security.BookingUser;
import com.reservation.models.security.JwtRequest;
import com.reservation.models.security.JwtResponse;
import com.reservation.models.security.Role;
import com.reservation.models.security.UserRole;
import com.reservation.securityconfig.JwtTokenUtil;
import com.reservation.services.BookingUserService;

@RestController
@CrossOrigin(maxAge = 3600)
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final BookingUserService userService;
	
	

	public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
			BookingUserService userService) {

		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
		
	}
	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			@Valid @RequestBody JwtRequest authenticationRequest ) throws Exception{
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		final UserDetails userDetails = this.userService.loadUserByUsername(authenticationRequest.getUsername());
		
		BookingUser username = this.userService.loadUserByUsername(authenticationRequest.getUsername());		
		
		final String token = this.jwtTokenUtil.generateToken(userDetails);
		
		HashSet<UserRole> roles = new HashSet<UserRole>();
		Role role = new Role();
		role.setRole("ROLE_USER");
		UserRole userRole = new UserRole(username, role);
		roles.add(userRole);
		username.setUserRoles(roles);
		
		return ResponseEntity.ok().body(
				new JwtResponse(token, 
						username.getId(), 
						username.getFirstName(), 
						username.getLastName(), 
						username.getEmail(),
						username.getUserRoles()));
	}
	
	
	private void authenticate(String username, String password) throws Exception{
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( username, password));
		} catch (DisabledException exception) {
			throw new Exception("USER DISABLED", exception);
		}
		catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID CREDENTIALS", e);
		}
	}

}
