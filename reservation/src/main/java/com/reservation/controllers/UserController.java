package com.reservation.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.exception.ResourceNotFoundException;
import com.reservation.models.security.BookingUser;
import com.reservation.models.security.Role;
import com.reservation.models.security.UserAvailability;
import com.reservation.models.security.UserRole;
import com.reservation.services.BookingUserService;
import com.reservation.services.EmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/user")
@Api(value = "User Creation and logout APIs")
public class UserController {

	private final BookingUserService userService;

	private final EmailService emailService;

	public UserController(BookingUserService userService, EmailService emailService) {
		this.userService = userService;
		this.emailService = emailService;
	}

	
	@PostMapping("/register")
	@ApiOperation(value = "Controller for user registration")
	public ResponseEntity<?> userRegistration(
			@ApiParam(value = "User object to be stored in the database", required = true)
			@Valid @RequestBody BookingUser user) throws Exception{
		try {
			HashSet<UserRole> roles = new HashSet<UserRole>();
			Role role = new Role();
			role.setRole("ROLE_USER");
			UserRole userRole = new UserRole(user, role);
			roles.add(userRole);
			Set<UserRole> userRoles = user.getUserRoles();
			if(userRoles.isEmpty()) {
				userService.createUser(user, roles);
			}
			else {
				this.userService.createUser(user, user.getUserRoles());
			}
			
			String html = "User with " + user.getUsername()+ " has been successfully created.";
			emailService.sendHtml("naabin@outlook.com", user.getEmail(), "User Registration", html);
			return ResponseEntity.ok().body(user);
		} catch (Exception e) {
			throw new Exception("Internal sever error.");
		}
		
		
	}
	
	
	@GetMapping
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Succefully retrieved lists of registered users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resouce."),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to access is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to find is either unavailabe or not found.")
	})
	@ApiOperation(value = "View all the list of registered users", response = List.class)
	public ResponseEntity<List<BookingUser>> getAllRegisteredUsers(){
		List<BookingUser> allUsers = userService.getAllUsers();
		
		return ResponseEntity.ok().body(allUsers);
	}
	
	@PostMapping("/checkuniqueuser")
	public ResponseEntity<?> checkUniqueUserAvailability(@RequestParam(required = true, name = "username")String username){
		boolean userAvailable = this.userService.uniqueUserAvailable(username.toLowerCase());
		HashMap<String, Boolean> available = new HashMap<String, Boolean>();
		available.put("available", false);
		UserAvailability userAvailability = new UserAvailability(available);
		if(userAvailable) {
			available.put("available", true);
			return ResponseEntity.ok().body(userAvailability);
		}
		available.put("available", false);
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(userAvailability);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<BookingUser> getRegisteredById(
			@ApiParam(value = "User id to retirieve the user information", required = true)@PathVariable("id") Long id) throws ResourceNotFoundException{
		BookingUser user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found which is associated with an id: " + id));
		return ResponseEntity.ok().body(user);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id){
		userService.deleteUserById(id);
		return ResponseEntity.ok().body("Resource deleted successfully.");
	}
	
	
	@PostMapping("/logout")
	@ApiOperation(value = "User logout API. However it does not guarantee that the token will expire and become invalid.")
	public ResponseEntity<?> logout(){
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok().body("Logget out successfully");
	}
	
	
}
