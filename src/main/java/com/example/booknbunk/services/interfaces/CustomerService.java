package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;

import java.util.List;

public interface CustomerService {

    CustomerDetailedDto customerToCustomerDetailedDto(Customer customer);
    CustomerMiniDto customerToCustomerMiniDto (Customer customer);

    Customer customerDetailedDtoToCustomer(CustomerDetailedDto customerDetailedDto);
    Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto);

    BookingMiniDto bookingToBookingMiniDto(Booking booking);
    Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto);


    List<CustomerDetailedDto> getAllCustomers();
}
