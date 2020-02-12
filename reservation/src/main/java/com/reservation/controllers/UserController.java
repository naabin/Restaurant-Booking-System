package com.reservation.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.exception.ResourceNotFoundException;
import com.reservation.models.security.BookingUser;
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
			userService.createUser(user, user.getUserRoles());
			String html = "User with " + user.getUsername()+ " has been successfully created.";
			emailService.sendHtml("naabin@outlook.com", user.getEmail(), "User Registration", html);
			return ResponseEntity.ok().body("User registration successful.");
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
