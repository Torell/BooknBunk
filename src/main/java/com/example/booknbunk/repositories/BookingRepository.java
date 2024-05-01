package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
