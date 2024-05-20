package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByCustomer(Customer customer);

}
