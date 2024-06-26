package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CustomerService {


    void createCustomer(String name, String email);

    void createCustomer(CustomerDetailedDto customerDetailedDto);

    void editCustomer(CustomerDetailedDto customerDetailedDto);

    //void deleteCustomer(long id); den gamla!

    String deleteCustomer(long id); // den nya som returnerar ett String meddelande

    CustomerDetailedDto findCustomerById(long id);

    public List<CustomerDetailedDto> getAllCustomersDetailedDto();

    List<BookingMiniDto> bookings = new ArrayList<>(); //???

    CustomerDetailedDto customerToCustomerDetailedDto(Customer customer);
    CustomerMiniDto customerToCustomerMiniDto (Customer customer);

    void updateCustomer(long id, String name, String email);

    Customer customerDetailedDtoToCustomer(CustomerDetailedDto customerDetailedDto);
    Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto);

    BookingMiniDto bookingToBookingMiniDto(Booking booking);
    Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto);





}
