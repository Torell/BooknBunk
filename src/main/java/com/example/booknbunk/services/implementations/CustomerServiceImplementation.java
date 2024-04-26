package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService {

    CustomerRepository customerRepo;

    @Autowired
    public CustomerServiceImplementation(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }


    // Söker ut alla kunder
    @Override
    public List<CustomerDetailedDto> getAllCustomersDetailedDto() {
        return customerRepo.findAll()
                .stream()
                .map(customer -> customerToCustomerDetailedDto(customer)).toList();
    }

    @Override
    public void createCustomer(CustomerDetailedDto customerDetailedDto){
        customerRepo.save(customerDetailedDtoToCustomer(customerDetailedDto));
    }

    //DetailedCustomerDto -> customer
    @Override
    public Customer customerDetailedDtoToCustomer(CustomerDetailedDto customerDetailedDto){
        return Customer.builder()
                .id(customerDetailedDto.getId())
                .name(customerDetailedDto.getName())
                .email(customerDetailedDto.getEmail())
                .bookings(customerDetailedDto.getBookingMiniDtoList()
                        .stream()
                        .map(bookingMiniDto ->  bookingMiniDtoToBooking(bookingMiniDto))
                        .toList()).build();
    }

    // customer -> customerDetailedDto
    @Override
    public CustomerDetailedDto customerToCustomerDetailedDto(Customer customer){
        return CustomerDetailedDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .bookingMiniDtoList(customer.getBookings()
                        .stream()
                        .map(booking -> bookingToBookingMiniDto(booking)).
                        toList()).build();
    }



    //miniDto -> customer
    @Override
    public Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto){
        return Customer.builder()
                .id(customerMiniDto.getId())
                .name(customerMiniDto.getName())
                .build();
    }

    // customer -> CustomerMiniDto
    @Override
    public CustomerMiniDto customerToCustomerMiniDto(Customer customer){
        return CustomerMiniDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .build();
    }



    @Override
    public BookingMiniDto bookingToBookingMiniDto(Booking booking) {
        return BookingMiniDto.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto) {
        return Booking.builder()
                .id(bookingMiniDto.getId())
                .startDate(bookingMiniDto.getStartDate())
                .endDate(bookingMiniDto.getEndDate())
                .build();
    }

}
