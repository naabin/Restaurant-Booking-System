package com.reservation.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.reservation.models.OpeningHours;
import com.reservation.models.Restaurant;
import com.reservation.repositories.OpeningHoursRepository;
import com.reservation.repositories.RestaurantRepository;
import com.reservation.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	
	private final RestaurantRepository restaurantRepository;
	private final OpeningHoursRepository openingHoursRepository;
	
	
	
	public RestaurantServiceImpl(RestaurantRepository restaurantRepository, OpeningHoursRepository openingHoursRepository) {
		this.restaurantRepository = restaurantRepository;
		this.openingHoursRepository = openingHoursRepository;
	}



	@Override
	public Restaurant createRestaurant(Restaurant restaurant) {
		Set<OpeningHours> openingHours = restaurant.getOpeningHours();
		for(OpeningHours openingHour: openingHours) {
			this.openingHoursRepository.save(openingHour);
		}
		return this.restaurantRepository.save(restaurant);
	}



	@Override
	public Restaurant updateRestaurant(Restaurant restaurant) {
		Set<OpeningHours> openingHours = restaurant.getOpeningHours();
		for(OpeningHours openingHour: openingHours) {
			this.openingHoursRepository.save(openingHour);
		}
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
		if(restaurant.isPresent()) {
			this.restaurantRepository.deleteById(id);
		}
	}



	@Override
	public void deleteRestaurant(Restaurant restaurant) {
		
		if(restaurant != null) {
			this.restaurantRepository.delete(restaurant);
		}
		
	}
	



}
