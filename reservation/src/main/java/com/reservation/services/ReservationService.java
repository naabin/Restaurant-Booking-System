package com.reservation.services;

import java.util.List;
import java.util.Optional;

import com.reservation.models.Reservation;

public interface ReservationService {
	
	Reservation createReservation(Reservation reservation);
	
	Reservation updateReservation(Reservation reservation);
	
	List<Reservation> getAllReservations();
	
	Optional<Reservation> findById(Long id);
	
	void deleteReservationById(Long id);
	
	void deleteReservation(Reservation reservation);
	
	
}
