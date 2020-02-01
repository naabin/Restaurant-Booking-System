package com.reservation.models;


import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "opening_hours")
public class OpeningHours {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	private String dayOfWeek;

	@NotNull
	private LocalTime openFrom = LocalTime.now();

	@NotNull
	private LocalTime openUntil = LocalTime.now();

	public Long getId() {
		return id;
	}

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
