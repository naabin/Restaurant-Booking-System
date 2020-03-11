package com.reservation.controllers;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.exception.ResourceNotFoundException;
import com.reservation.models.Restaurant;
import com.reservation.models.security.BookingUser;
import com.reservation.services.BookingUserService;
import com.reservation.services.RestaurantService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/restaurant")
@Api(value = "Restaurant CRUD")
public class RestautantController {
	
	private final RestaurantService restaurantService;
	
	private final BookingUserService userService;
	
	public RestautantController(RestaurantService restaurantService, BookingUserService userService) {
		this.restaurantService = restaurantService;
		this.userService = userService;
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Restaurant>> getAllRestaurant(){
		List<Restaurant> allRestaurants = this.restaurantService.getAllRestaurants();
		
		return ResponseEntity.ok().body(allRestaurants);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findRestaurantById(@PathVariable("id") Long id, @RequestParam(name = "userId", required = true)Long userId) throws ResourceNotFoundException{
		Restaurant restaurant = this.restaurantService.findRestaurantById(id).orElseThrow(() ->
				new ResourceNotFoundException("Restaurant with an id " + id  +" could not be found."));
		if(restaurant.getUser().getId().equals(userId)) {
			return ResponseEntity.ok().body(restaurant);
		}
		else {
			return ResponseEntity.badRequest().body("User not associated with this id");
		}
		
		
	}
	
	
	@PostMapping("/new")
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant, @RequestParam(name = "userId", required = true)Long userId){
		BookingUser user = this.userService.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
			restaurant.setUser(user);
			Restaurant createdRestaurant = this.restaurantService.createRestaurant(restaurant, restaurant.getOpeningHours());
			return ResponseEntity.ok().body(createdRestaurant);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRestaurant(@PathVariable("id") Long id) throws ResourceNotFoundException{
		Restaurant restaurant = this.restaurantService.findRestaurantById(id).orElseThrow(() -> new ResourceNotFoundException("resource could not be found"));
		this.restaurantService.updateRestaurant(restaurant, null);
		
		return ResponseEntity.ok().body("Restaurant successfully updated.");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRestaurantById(@PathVariable("id") Long id){
		this.restaurantService.deleteRestaurantById(id);
		return ResponseEntity.ok().body("Restaurant deleted Successfully");
	}
	
	
}
