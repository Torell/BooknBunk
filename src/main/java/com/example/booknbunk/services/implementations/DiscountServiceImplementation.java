package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.services.interfaces.DiscountService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
        long nightsBooked = ChronoUnit.DAYS.between(bookingDetailedDto.getStartDate(),bookingDetailedDto.getEndDate());
        return nightsBooked >= 2;
    }

    @Override
    public boolean tenOrMoreNightsThisYear(BookingDetailedDto bookingDetailedDto) {
        LocalDate oneYearFromToday = LocalDate.now().minusYears(1);
        LocalDate today = LocalDate.now();
        List<Booking> allBookingsMadeByCustomer = bookingRepository.findAllByCustomer(customerRepository.getReferenceById(bookingDetailedDto.getCustomerMiniDto().getId())).stream()
                .filter(booking -> booking.getStartDate().isAfter(oneYearFromToday) && booking.getStartDate().isBefore(today))
                .toList();;


        long nightsBooked = allBookingsMadeByCustomer.stream()
                .mapToLong(booking -> ChronoUnit.DAYS.between(booking.getStartDate(),booking.getEndDate()))
                .sum();

        System.out.println("nights booked last year " + nightsBooked);

        return nightsBooked >= 10;
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

    public double discountSundayToMonday(BookingDetailedDto bookingDetailedDto) {
        double numberOfSundayToMondayNights = 0;
        LocalDate dateToCheck = bookingDetailedDto.getStartDate();
        LocalDate endDate = bookingDetailedDto.getEndDate();

        while (dateToCheck.isBefore(endDate))  {
            if (dateToCheck.getDayOfWeek() ==  DayOfWeek.SUNDAY && dateToCheck.plusDays(1).getDayOfWeek() == DayOfWeek.MONDAY) {
                numberOfSundayToMondayNights++;
            }
           dateToCheck = dateToCheck.plusDays(1);
        }

        return (bookingDetailedDto.getRoomMiniDto().getPricePerNight() * 0.98) * numberOfSundayToMondayNights; //returns the total to be removed from price
    }
}
