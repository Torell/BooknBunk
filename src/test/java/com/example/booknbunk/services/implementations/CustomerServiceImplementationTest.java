package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest
class CustomerServiceImplementationTest {

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private CustomerServiceImplementation service = new CustomerServiceImplementation(customerRepo);


    long customerId = 1L;
    String customerName = "Steffi";
    String customerEmail = "steffi@gmail.com";
    List<BookingMiniDto> bookingMiniDtoList = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();
    Customer customer = new Customer(customerId, customerName, customerEmail,bookings);
    Booking booking = new Booking();
    CustomerMiniDto customerMiniDto = new CustomerMiniDto(customerId, customerName);

    CustomerDetailedDto customerDetailedDto = CustomerDetailedDto.builder().id(customerId).
            name(customerName).email(customerEmail).bookingMiniDtoList(bookingMiniDtoList).build();


    @Test
    void deleteCustomer() {
         when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
         service.deleteCustomer(customerId);
         verify(customerRepo, times(1)).findById(customerId);
         verify(customerRepo, times(1)).deleteById(customerId);
    }

    @Test
    void findCustomerById() {
            when(customerRepo.findById(customer.getId())).thenReturn(Optional.of(customer));

            CustomerDetailedDto foundCustomer = service.findCustomerById(customer.getId());

            verify(customerRepo, times(1)).findById(customer.getId());

            assertEquals(customer.getId(), foundCustomer.getId());
            assertEquals(customer.getName(), foundCustomer.getName());
            assertEquals(customer.getEmail(), foundCustomer.getEmail());
    }

    @Test
    void getAllCustomersDetailedDto() {

    }

    @Test
    void createCustomer() {
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);
        service.createCustomer(customerName, customerEmail);
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    @Test
    void customerDetailedDtoToCustomer() {
        Customer actual = service.customerDetailedDtoToCustomer(customerDetailedDto);
        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
        assertEquals(actual.getEmail(), customer.getEmail());
    }

    @Test
    void customerToCustomerDetailedDto() {
       CustomerDetailedDto actual = service.customerToCustomerDetailedDto(customer);

        assertEquals(actual.getId(), customerDetailedDto.getId());
        assertEquals(actual.getName(), customerDetailedDto.getName());
        assertEquals(actual.getEmail(), customerDetailedDto.getEmail());

        assertIterableEquals(actual.getBookingMiniDtoList(), customerDetailedDto.getBookingMiniDtoList());
    }

    @Test
    void customerMiniDtoToCustomer() {
        Customer actual = service.customerMiniDtoToCustomer(customerMiniDto);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());

    }

    @Test
    void customerToCustomerMiniDto() {
        CustomerMiniDto actual = service.customerToCustomerMiniDto(customer);

        assertSame(actual.getId(), customer.getId());
        assertEquals(actual.getName(), customer.getName());
    }
}