package com.reservation.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.reservation.models.Reservation;
import com.reservation.repositories.ReservationRepository;
import com.reservation.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	private ReservationRepository reservationRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public Reservation createReservation(Reservation reservation) {
		return this.reservationRepository.save(reservation);
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		
		return this.reservationRepository.save(reservation);
	}

	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		this.reservationRepository.findAll().forEach(reservations :: add);
		return reservations;
	}

	@Override
	public Reservation findById(Long id) {
		Optional<Reservation> reservation = this.reservationRepository.findById(id);
		return reservation.orElse(null);
	}

	@Override
	public void deleteReservationById(Long id) {
		this.reservationRepository.deleteById(id);
	}

	@Override
	public void deleteReservation(Reservation reservation) {
		this.reservationRepository.delete(reservation);

	}

}
