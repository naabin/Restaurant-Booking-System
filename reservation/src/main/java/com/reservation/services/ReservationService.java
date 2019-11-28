package com.reservation.services;

import java.util.List;

import com.reservation.models.Reservation;

public interface ReservationService {
	
	Reservation createReservation(Reservation reservation);
	
	Reservation updateReservation(Reservation reservation);
	
	List<Reservation> getAllReservations();
	
	Reservation findById(Long id);
	
	void deleteReservationById(Long id);
	
	void deleteReservation(Reservation reservation);
	
	
}
