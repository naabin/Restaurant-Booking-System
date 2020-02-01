package com.reservation.controllers;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.exception.ResourceNotFoundException;
import com.reservation.models.Restaurant;
import com.reservation.services.RestaurantService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/restaurant")
@Api(value = "Restaurant CRUD")
public class RestautantController {
	
	private final RestaurantService restaurantService;
	
	public RestautantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Restaurant>> getAllRestaurant(){
		List<Restaurant> allRestaurants = this.restaurantService.getAllRestaurants();
		
		return ResponseEntity.ok().body(allRestaurants);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(@PathVariable("id") Long id) throws ResourceNotFoundException{
		Restaurant restaurant = this.restaurantService.findRestaurantById(id).orElseThrow(() ->
				new ResourceNotFoundException("Restaurant with an id " + id  +" could not be found."));
		
		return ResponseEntity.ok().body(restaurant);
	}
	
	
	@PostMapping("/new")
	public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant){
		Restaurant createdRestaurant = this.restaurantService.createRestaurant(restaurant);
		
		return ResponseEntity.ok().body("Restaurant created with an id " + createdRestaurant.getId());
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRestaurant(@PathVariable("id") Long id) throws ResourceNotFoundException{
		Restaurant restaurant = this.restaurantService.findRestaurantById(id).orElseThrow(() -> new ResourceNotFoundException("resource could not be found"));
		this.restaurantService.updateRestaurant(restaurant);
		
		return ResponseEntity.ok().body("Restaurant successfully updated.");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRestaurantById(@PathVariable("id") Long id){
		this.restaurantService.deleteRestaurantById(id);
		return ResponseEntity.ok().body("Restaurant deleted Successfully");
	}
	
	
}
