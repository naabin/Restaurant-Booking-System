package com.reservation.controllers.publiccontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.controllers.publiccontrollers.response.RestaurantResponse;
import com.reservation.models.Restaurant;
import com.reservation.services.RestaurantService;

@RestController
@RequestMapping("/api/public/restaurant")
public class RestaurantController {
	
	private final RestaurantService restaurantService;
	
	public RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getRestaurants(){
		List<Restaurant> allRestaurants = this.restaurantService.getAllRestaurants();
		List<RestaurantResponse> responses = new ArrayList<RestaurantResponse>();
		
		for(Restaurant r: allRestaurants) {
			RestaurantResponse response = 
					new RestaurantResponse(r.getId(), r.getName(), r.getEmail(), r.getPhoneNumber(), r.getSiteAddress(),  r.getOpeningHours());
			responses.add(response);
		}
		
		return ResponseEntity.ok().body(responses);
	}
	
	
}
