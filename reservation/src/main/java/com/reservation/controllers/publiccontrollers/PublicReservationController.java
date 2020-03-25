package com.reservation.controllers.publiccontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reservation.exception.ResourceNotFoundException;
import com.reservation.models.Reservation;
import com.reservation.models.Restaurant;
import com.reservation.services.ReservationService;
import com.reservation.services.RestaurantService;

@RestController
@RequestMapping("/api/public/reservation")
public class PublicReservationController {
	
	private final RestaurantService restaurantService;
	private final ReservationService reservationService;
	
	public PublicReservationController(
			RestaurantService restaurantService,
			ReservationService reservationService) {
		this.restaurantService = restaurantService;
		this.reservationService = reservationService;
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> createReservation(
			@RequestParam("restaurant") String restaurnatName,
			@RequestBody Reservation reservation) throws ResourceNotFoundException{
		Restaurant restaurant = this.restaurantService.findRestaurant(restaurnatName)
			.orElseThrow(() -> new ResourceNotFoundException("Restaurant could not be founds"));
		reservation.setRestaurant(restaurant);
		this.reservationService.createReservation(reservation);
		return ResponseEntity.ok().build();
		
	}
}
