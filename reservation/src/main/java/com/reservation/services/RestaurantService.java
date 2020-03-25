package com.reservation.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.reservation.models.OpeningHours;
import com.reservation.models.Restaurant;

public interface RestaurantService {
	Restaurant createRestaurant(Restaurant restaurant, Set<OpeningHours> list);
	Restaurant updateRestaurant(Restaurant restaurant);
	Page<Restaurant> getAllRestaurants(Integer pageNumber, Integer pageSize, String name);
	Optional<Restaurant> findRestaurantById(Long id);
	void deleteRestaurantById(Long id);
	void deleteRestaurant(Restaurant restaurant);
	Optional<Restaurant> findRestaurant(String restaurantName);
}
