package com.reservation.controllers;



import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.models.security.BookingUser;
import com.reservation.models.security.JwtRequest;
import com.reservation.models.security.JwtResponse;
import com.reservation.securityconfig.JwtTokenUtil;
import com.reservation.services.BookingUserService;

@RestController
@CrossOrigin(maxAge = 3600)
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtTokenUtil jwtTokenUtil;

	private final BookingUserService userService;
	

	

	public JwtAuthenticationController(
			AuthenticationManager authenticationManager, 
			JwtTokenUtil jwtTokenUtil,
			BookingUserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userService = userService;
		
		
	}
	
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(
			@Valid @RequestBody JwtRequest authenticationRequest ) throws Exception{
		
		final BookingUser userDetails = this.userService.loadUserByEmail(authenticationRequest.getUsername());
		
		if(userDetails == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credential provided");
		}
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		
		
		BookingUser username = this.userService.loadUserByEmail(authenticationRequest.getUsername());		
		
		final String token = this.jwtTokenUtil.generateToken(userDetails);
		
		if(username.getRestaurant() != null) {
			final Long restaurantId = username.getRestaurant().getId();
			return ResponseEntity.ok().body(
					new JwtResponse(token, 
							username.getId(), 
							username.getFirstName(), 
							username.getLastName(), 
							username.getEmail(),
							username.getUserRoles(),
							restaurantId
							));
		}
		else {
			return ResponseEntity.ok().body(
					new JwtResponse(token, 
							username.getId(), 
							username.getFirstName(), 
							username.getLastName(), 
							username.getEmail(),
							username.getUserRoles()
							));
		}
		

	}
	
	
	private void authenticate(String username, String password) throws Exception{
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException exception) {
			throw new Exception("USER DISABLED", exception);
		}
		catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID CREDENTIALS", e);
		}
	}

}
