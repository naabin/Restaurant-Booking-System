package com.reservation.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.reservation.models.OpeningHours;
import com.reservation.models.Restaurant;

public interface RestaurantService {
	Restaurant createRestaurant(Restaurant restaurant, Set<OpeningHours> openingHours);
	Restaurant updateRestaurant(Restaurant restaurant);
	List<Restaurant> getAllRestaurants();
	Optional<Restaurant> findRestaurantById(Long id);
	void deleteRestaurantById(Long id);
	void deleteRestaurant(Restaurant restaurant);
}
