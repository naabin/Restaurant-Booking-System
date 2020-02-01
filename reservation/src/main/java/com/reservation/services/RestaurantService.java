package com.reservation.services;

import java.util.List;
import java.util.Optional;

import com.reservation.models.Restaurant;

public interface RestaurantService {
	Restaurant createRestaurant(Restaurant restaurant);
	Restaurant updateRestaurant(Restaurant restaurant);
	List<Restaurant> getAllRestaurants();
	Optional<Restaurant> findRestaurantById(Long id);
	void deleteRestaurantById(Long id);
	void deleteRestaurant(Restaurant restaurant);
}
