package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.services.interfaces.DiscountService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountServiceImplementation implements DiscountService {

    BookingRepository bookingRepository;
    CustomerRepository customerRepository;

    public DiscountServiceImplementation(BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean twoOrMoreNights(BookingDetailedDto bookingDetailedDto) {
        return bookingDetailedDto.getStartDate().isBefore(bookingDetailedDto.getEndDate());
    }

    @Override
    public boolean tenOrMoreNightsThisYear(BookingDetailedDto bookingDetailedDto) {
        LocalDate oneYearFromToday = LocalDate.now().minusYears(1);
        List<Booking> allBookingsMadeByCustomer = bookingRepository.findAllByCustomer(customerRepository.getReferenceById(bookingDetailedDto.getCustomerMiniDto().getId())).stream()
                .filter(booking -> booking.getStartDate().isBefore(oneYearFromToday))
                .toList();


        return allBookingsMadeByCustomer.size() >= 10;
    }

    @Override
    public double discount(BookingDetailedDto bookingDetailedDto) {
       double multiplier = 1;
       if (twoOrMoreNights(bookingDetailedDto)) {
           multiplier -= 0.005;
       }
       if (tenOrMoreNightsThisYear(bookingDetailedDto)) {
           multiplier -= 0.02;
       }
       return multiplier;
    }
}
