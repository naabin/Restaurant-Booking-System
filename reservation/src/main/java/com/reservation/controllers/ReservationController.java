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

import com.reservation.models.Reservation;
import com.reservation.services.ReservationService;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}



	@GetMapping("/all")
	public ResponseEntity<List<Reservation>> getAllReservation(){
		List<Reservation> allReservations = this.reservationService.getAllReservations();
		
		return ResponseEntity.ok().body(allReservations);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> getReservationById(@PathVariable("id") Long id){
		Reservation reservation = this.reservationService.findById(id);
		return ResponseEntity.ok().body(reservation);
	}
	
	@PostMapping
	public ResponseEntity<?> createReservation(@RequestBody Reservation reservation){
		Reservation savedReservation = this.reservationService.createReservation(reservation);
		
		return ResponseEntity.ok().body("Reservation is created with an id " + savedReservation.getId());

	}
	
	@PutMapping
	public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation){
		Reservation updateReservation = this.reservationService.updateReservation(reservation);
		
		return ResponseEntity.ok().body("Reservation with an id " + updateReservation.getId() + " has successfully been updated");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReservationById(@PathVariable("id") Long id){
		this.reservationService.deleteReservationById(id);
		return ResponseEntity.ok().body("Reservation deleted");
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteReservation(@RequestBody Reservation reservation){
		this.reservationService.deleteReservation(reservation);
		return ResponseEntity.ok().body("Reservation deleted successfully");
	}
	
	
}
