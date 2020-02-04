package com.reservation.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "restaurant")
@ApiModel(description = "Details of Restaurant")
public class Restaurant extends Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String address;

	@NotNull
	private String phoneNumber;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<OpeningHours> openingHours = new HashSet<OpeningHours>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Reservation> bookings = new ArrayList<Reservation>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<OpeningHours> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(Set<OpeningHours> openingHours) {
		this.openingHours = openingHours;
	}

	public List<Reservation> getBookings() {
		return bookings;
	}

	public void setBookings(List<Reservation> bookings) {
		this.bookings = bookings;
	}
	
	

}
