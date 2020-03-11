package com.reservation.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.reservation.models.OpeningHours;
import com.reservation.models.Reservation;
import com.reservation.models.Restaurant;
import com.reservation.models.security.BookingUser;
import com.reservation.repositories.OpeningHoursRepository;
import com.reservation.repositories.RestaurantRepository;
import com.reservation.services.BookingUserService;
import com.reservation.services.ReservationService;
import com.reservation.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final OpeningHoursRepository openingHoursRepository;
	private final ReservationService reservationService;
	private final BookingUserService userService;

	public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
			OpeningHoursRepository openingHoursRepository, ReservationService reservationService,
			BookingUserService userService) {
		this.restaurantRepository = restaurantRepository;
		this.openingHoursRepository = openingHoursRepository;
		this.reservationService = reservationService;
		this.userService = userService;
	}

	@Override
	@Transactional
	public Restaurant createRestaurant(Restaurant restaurant, Set<OpeningHours> openingHours) {
		for (OpeningHours openingHour : openingHours) {
			openingHour.setRestaurant(restaurant);
			this.openingHoursRepository.save(openingHour);
		}
		if(restaurant.getUser() != null) {
			Optional<BookingUser> optionalUser = this.userService.findUserById(restaurant.getUser().getId());
			if(optionalUser.isPresent()) {
				BookingUser bookingUser = optionalUser.get();
				bookingUser.setRestaurant(restaurant);
				this.userService.updateUser(bookingUser, bookingUser.getUserRoles());
			}
			
		}
		restaurant.getOpeningHours().addAll(openingHours);
		return this.restaurantRepository.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Restaurant restaurant, Reservation reservation) {
		Set<OpeningHours> openingHours = restaurant.getOpeningHours();
		for (OpeningHours openingHour : openingHours) {
			this.openingHoursRepository.saveAndFlush(openingHour);
		}

		Reservation updatedReservation = this.reservationService.updateReservation(reservation);
	
		restaurant.getOpeningHours().addAll(openingHours);
		restaurant.getBookings().add(updatedReservation);
		return this.restaurantRepository.saveAndFlush(restaurant);
	}

	@Override
	public List<Restaurant> getAllRestaurants() {

		return this.restaurantRepository.findAll();
	}

	@Override
	public Optional<Restaurant> findRestaurantById(Long id) {

		return this.restaurantRepository.findById(id);
	}

	@Override
	public void deleteRestaurantById(Long id) {
		Optional<Restaurant> restaurant = this.restaurantRepository.findById(id);
		if (restaurant.isPresent()) {
			this.restaurantRepository.deleteById(id);
		}
	}

	@Override
	public void deleteRestaurant(Restaurant restaurant) {

		if (restaurant != null) {
			this.restaurantRepository.delete(restaurant);
		}

	}

}
