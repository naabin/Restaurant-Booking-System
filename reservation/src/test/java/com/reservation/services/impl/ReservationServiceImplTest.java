package com.reservation.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.reservation.models.Reservation;
import com.reservation.repositories.ReservationRepository;
import com.reservation.services.ReservationService;

class ReservationServiceImplTest {
	
	@Mock
	private ReservationRepository reservationRepository;
	
	@InjectMocks
	private ReservationService reservationService;
	
	private Reservation reservation;
	
	private final Long ID = 1L;
	
	

	@BeforeEach
	void setUp() throws Exception {
		this.reservation = new Reservation();
		reservation.setId(this.ID);
		reservation.setFullName("John Doe");
		reservation.setEmail("john@email.com");
		reservation.setPhoneNumber("0123456789");
		reservation.setNumberOfPeople(2);
		reservation.setDate(LocalDate.now());
		}

	@Test
	void testCreateReservation() {
		Reservation r1 = new Reservation();
		r1.setId(ID);
		r1.setFullName("John");
		r1.setEmail("john@email.com");
		r1.setPhoneNumber("01234556789");
		r1.setNumberOfPeople(2);
		r1.setDate(LocalDate.now());
		r1.setSpecialRequest("This is test");
		
		Reservation savedReservation = this.reservationService.createReservation(r1);
		
		assertNotNull(savedReservation);
		
		assertEquals(this.ID, savedReservation.getId());
	}

	@Test
	void testUpdateReservation() {
		this.reservation.setId(2L);
		this.reservation.setFullName("Tony");
		Reservation updatedReservation = this.reservationService.createReservation(this.reservation);
		assertEquals(2L, updatedReservation.getId());
		assertEquals("Tony", updatedReservation.getFullName());
	}

	@Test
	void testGetAllReservations() {
		List<Reservation> reservations = new ArrayList<Reservation>();
		for(long i = 0; i < 3; i++) {
			Reservation reservation = new Reservation();
			reservation.setId(i);
			reservation.setFullName("Test " + i);
			reservations.add(reservation);
		}
		
		when(this.reservationRepository.findAll()).thenReturn(reservations);
		List<Reservation> allReservations = this.reservationService.getAllReservations();
		
		assertNotNull(allReservations);
		assertEquals(3, allReservations.size());
	}

	@Test
	void testFindById() {
		when(this.reservationRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.reservation));
		Reservation reservationById = this.reservationService.findById(this.reservation.getId());
		
		assertNotNull(reservationById);
		assertEquals(this.ID, reservationById.getId());
	}
	
	@Test
	void testNotFoundById() {
		when(this.reservationRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		Reservation findById = this.reservationService.findById(ID);
		
		assertNull(findById);
		
	}

	@Test
	void testDeleteReservationById() {
		this.reservationService.deleteReservationById(ID);
		verify(this.reservationRepository).deleteById(Mockito.anyLong());
	}

	@Test
	void testDeleteReservation() {
		this.reservationService.deleteReservation(reservation);
		verify(this.reservationRepository).delete(Mockito.any());
	}

}
