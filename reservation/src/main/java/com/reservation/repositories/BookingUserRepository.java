package com.reservation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reservation.models.security.BookingUser;

@Repository
public interface BookingUserRepository extends JpaRepository<BookingUser, Long> {

	BookingUser findByUsername(String name);
}
