package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.BookingMiniDto;

public interface DiscountService {

    boolean twoOrMoreNights(BookingDetailedDto bookingDetailedDto);
    boolean tenOrMoreNightsThisYear(BookingDetailedDto bookingDetailedDto);

    double discount(BookingDetailedDto bookingDetailedDto);

    double discountSundayToMonday(BookingDetailedDto bookingDetailedDto);
}
