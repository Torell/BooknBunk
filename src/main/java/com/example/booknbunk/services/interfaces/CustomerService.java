package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;

public interface CustomerService {


    CustomerMiniDto customerToCustomerMiniDto (Customer customer);

 ;

    Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto);

    //DetailedCustomerDto -> customer
    Customer customerDetailedDtoToCustomer(CustomerDetailedDto customerDetailedDto);

    // customer -> customerDetailedDto
    CustomerDetailedDto customerToCustomerDetailedDto(Customer customer);

    BookingMiniDto bookingToBookingMiniDto(Booking booking);

    //MiniDto -> customer
    Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto);
}
