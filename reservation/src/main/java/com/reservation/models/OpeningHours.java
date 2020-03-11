package com.reservation.models;


import java.time.LocalTime;

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

	public LocalTime getOpenFrom() {
		return openFrom;
	}

	public void setOpenFrom(LocalTime openFrom) {
		this.openFrom = openFrom;
	}

	public LocalTime getOpenUntil() {
		return openUntil;
	}

	public void setOpenUntil(LocalTime openUntil) {
		this.openUntil = openUntil;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String dayOfWeek;

	@Column
	private LocalTime openFrom = LocalTime.now();

	@Column
	private LocalTime openUntil = LocalTime.now();

	public Long getId() {
		return id;
	}
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Restaurant restaurant;

	public void setId(Long id) {
		this.id = id;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getFrom() {
		return openFrom;
	}

	public void setFrom(LocalTime from) {
		this.openFrom = from;
	}

	public LocalTime getTo() {
		return openUntil;
	}

	public void setTo(LocalTime to) {
		this.openUntil = to;
	}


}
