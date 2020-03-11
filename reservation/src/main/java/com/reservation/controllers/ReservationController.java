package com.reservation.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
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
import com.reservation.models.Reservation;
import com.reservation.models.Restaurant;
import com.reservation.services.ReservationService;
import com.reservation.services.RestaurantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/reservation")
@Api(value = "Reservation CRUD")
public class ReservationController {
	
	private final ReservationService reservationService;
	private final RestaurantService restaurantService;
	
	public ReservationController(ReservationService reservationService, RestaurantService restaurantService) {
		this.reservationService = reservationService;
		this.restaurantService = restaurantService;	
	}
	@ApiOperation(value = "View all the list of reservations", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Succefully retrieved lists of reservation"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resouce."),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to access is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to find is either unavailabe or not found.")
	})
	@GetMapping("/all")
	public ResponseEntity<List<Reservation>> getAllReservation(@RequestParam("restaurantId") long restaurantId) throws ResourceNotFoundException{
		
		Restaurant restaurant = this.restaurantService.findRestaurantById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("No such restaurant found."));
				
		if(restaurant.getId().equals(restaurantId)) {
			return ResponseEntity.ok().body(restaurant.getBookings());
		}
		else {
			return ResponseEntity.badRequest().body(null);
		}
		
		
	}
	
	
	@ApiOperation(value = "Retrieve reservation by an ID")
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id, @RequestParam("restaurantId")Long restuarantId) throws ResourceNotFoundException{
		Restaurant restaurant = this.restaurantService.findRestaurantById(restuarantId).orElseThrow(() -> new ResourceNotFoundException("No Such restaurant found.") );
		Reservation reservation = 	this.reservationService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservtion id:  " 
										+ id + " could not be found." ));
		
		if(reservation.getRestaurant().getId().equals(restaurant.getId())) {
			return ResponseEntity.ok().body(reservation);
		}
		else {
			return ResponseEntity.badRequest().body(null);
		}
		
		
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> createReservation(
			@ApiParam(value = "Reservation object store in a database table", required = true)
			@Valid 
			@RequestBody Reservation reservation,
			@RequestParam("restaurantId")Long restaurantId) throws ResourceNotFoundException{
		
		Restaurant restaurant = this.restaurantService.findRestaurantById(restaurantId).orElseThrow(() -> new ResourceNotFoundException("No such restaurant found."));
		reservation.setRestaurant(restaurant);
		this.restaurantService.updateRestaurant(restaurant, reservation);
		return ResponseEntity.ok().body("Reservation is created");

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateReservation(
			@ApiParam(value = "Reservation id to update reservation object", required = true)
			@PathVariable("id") Long id,
			@ApiParam(value = "Reservation object store in a database table", required = true)
			@Valid
			@RequestBody Reservation updatingReservation) throws ResourceNotFoundException{
		
		Reservation reservation = this.reservationService.findById(id).orElseThrow(() -> 
			new ResourceNotFoundException("Reservation not found with an id " + id)
		);
		
		reservation.setId(updatingReservation.getId());
		reservation.setFullName(updatingReservation.getFullName());
		reservation.setDate(updatingReservation.getDate());
		reservation.setNumberOfPeople(updatingReservation.getNumberOfPeople());
		reservation.setPhoneNumber(updatingReservation.getPhoneNumber());
		reservation.setEmail(updatingReservation.getEmail());
		reservation.setSpecialRequest(updatingReservation.getSpecialRequest());
		
		Reservation updateReservation = this.reservationService.updateReservation(reservation);
		
		return ResponseEntity.ok().body("Reservation with an id " + updateReservation.getId() + " has successfully been updated");
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) throws ResourceNotFoundException{
		this.reservationService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found with an id " + id));
		this.reservationService.deleteReservationById(id);
		
		this.reservationService.deleteReservation(reservation);
		
		return ResponseEntity.ok().body("Reservation deleted successfully");
	}
	
	
}
