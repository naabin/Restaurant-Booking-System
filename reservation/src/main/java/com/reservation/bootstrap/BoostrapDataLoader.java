package com.reservation.bootstrap;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.reservation.models.Reservation;
import com.reservation.services.ReservationService;

@Component
public class BoostrapDataLoader implements CommandLineRunner {
	
	private final ReservationService reservationService;
	
	
	@Autowired
	public BoostrapDataLoader(ReservationService reservationService) {
		
		this.reservationService = reservationService;
	}



	@Override
	public void run(String... args) throws Exception {
		Reservation reservation = new Reservation();
		reservation.setId(1L);
		reservation.setFullName("Nabin Karki");
		reservation.setEmail("naabin@outlook.com");
		reservation.setNumberOfPeople(2);
		reservation.setPhoneNumber("012345678");
		reservation.setDate(LocalDate.now());
		reservation.setSpecialRequest("I would like some food without any meat on it.");
		
		this.reservationService.createReservation(reservation);
		
		
		Reservation reservation2 = new Reservation();
		reservation2.setId(2L);
		reservation2.setFullName("Thomas Wayne");
		reservation2.setEmail("thomas@batman.com");
		reservation2.setDate(LocalDate.now());
		reservation2.setPhoneNumber("012345678");
		reservation2.setSpecialRequest("I would like some meat of bats.");
		reservation2.setNumberOfPeople(1);
		
		this.reservationService.createReservation(reservation2);
		
		System.out.println("Loading dummy data via bootstrap loader...");
		
	}

}
