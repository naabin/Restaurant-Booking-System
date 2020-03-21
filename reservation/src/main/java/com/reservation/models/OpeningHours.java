package com.reservation.models;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "opening_hours")
public class OpeningHours {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String dayOfWeek;

	@Column
	private LocalDateTime openFrom;;

	@Column
	private LocalDateTime openUntil;

	public Long getId() {
		return id;
	}
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Restaurant restaurantOpeningHours;

	public void setId(Long id) {
		this.id = id;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalDateTime getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(LocalDateTime openFrom) {
		this.openFrom = openFrom;
	}

	public LocalDateTime getOpenUntil() {
		return openUntil;
	}

	public void setOpenUntil(LocalDateTime openUntil) {
		this.openUntil = openUntil;
	}

	public Restaurant getRestaurantOpeningHours() {
		return restaurantOpeningHours;
	}

	public void setRestaurantOpeningHours(Restaurant restaurantOpeningHours) {
		this.restaurantOpeningHours = restaurantOpeningHours;
	}

	

}
