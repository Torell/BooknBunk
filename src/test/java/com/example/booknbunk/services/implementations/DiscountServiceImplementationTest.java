package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.*;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.services.interfaces.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DiscountServiceImplementationTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CustomerRepository customerRepository;
    private DiscountService discountService;
    private DiscountService discountServiceSpy;

    @BeforeEach()
    void setup() {
       discountService = new DiscountServiceImplementation(bookingRepository,customerRepository);
       discountServiceSpy = spy(discountService);
    }

    @Test
    void twoOrMoreNightsTest() {
        BookingDetailedDto oneNightBooking = new BookingDetailedDto(1L, LocalDate.now(),LocalDate.now().plusDays(1),new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);
        BookingDetailedDto twoNightsBooking = new BookingDetailedDto(1L, LocalDate.now(),LocalDate.now().plusDays(2),new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);

       boolean shouldBeFalse = discountService.twoOrMoreNights(oneNightBooking);
       boolean shouldBeTrue = discountService.twoOrMoreNights(twoNightsBooking);

        assertFalse(shouldBeFalse);
        assertTrue(shouldBeTrue);


    }

    @Test
    void tenOrMoreNightsThisYearTest() {
        CustomerDetailedDto c1 = new CustomerDetailedDto(1L,"TwoYears","email@email.com", new ArrayList<>());
        CustomerDetailedDto c2 = new CustomerDetailedDto(2L,"TenNights","email@email.com", new ArrayList<>());
        CustomerDetailedDto c3 = new CustomerDetailedDto(3L,"FiveNights","email@email.com", new ArrayList<>());

        Customer c1Customer = new Customer(1L,"email@email.com","TwoYears");
        Customer c2Customer = new Customer(2L,"email@email.com","TenNights");
        Customer c3Customer = new Customer(3L,"email@email.com","FiveNights");

        CustomerMiniDto twoYearTenNightCustomer = new CustomerMiniDto(1L,"TwoYears");
        CustomerMiniDto tenNightCustomer = new CustomerMiniDto(2L,"TenNights");
        CustomerMiniDto fiveNightCustomer = new CustomerMiniDto(3L,"FiveNights");

        BookingDetailedDto tenNightsTwoYearsAgoDTO = new BookingDetailedDto(1L, LocalDate.now().minusYears(2),LocalDate.now().minusYears(2).plusDays(11),twoYearTenNightCustomer, new RoomMiniDto(1L,0,100),0,0);
        BookingDetailedDto tenNightsThisYearDTO = new BookingDetailedDto(2L, LocalDate.now().minusMonths(1),LocalDate.now().minusMonths(1).plusDays(11),tenNightCustomer, new RoomMiniDto(1L,0,100),0,0);
        BookingDetailedDto fiveNightsThisYearDTO = new BookingDetailedDto(3L, LocalDate.now().minusMonths(1),LocalDate.now().minusMonths(1).plusDays(5),fiveNightCustomer, new RoomMiniDto(1L,0,100),0,0);

        Booking tenNightsTwoYearsAgoBooking = new Booking(1L,LocalDate.now().minusYears(2),LocalDate.now().minusYears(2).plusDays(11), new Room(),0,new Customer(),0);
        Booking tenNightsThisYearBooking = new Booking(2L, LocalDate.now().minusMonths(1),LocalDate.now().minusMonths(1).plusDays(11), new Room(),0, new Customer(),0);
        Booking fiveNightsThisYearBooking = new Booking(3L, LocalDate.now().minusMonths(1),LocalDate.now().minusMonths(1).plusDays(5), new Room(),0, new Customer(),0);
        BookingMiniDto tenNightsTwoYearsAgoMini = new BookingMiniDto(1L, LocalDate.now().minusYears(2),LocalDate.now().minusYears(2).plusDays(11));
        BookingMiniDto tenNightsThisYearMini = new BookingMiniDto(2L, LocalDate.now().minusMonths(1),LocalDate.now().minusMonths(1).plusDays(11));
        BookingMiniDto fiveNightsThisYearMini = new BookingMiniDto(3L, LocalDate.now().minusMonths(1),LocalDate.now().minusMonths(1).plusDays(5));

        c1.setBookingMiniDtoList(List.of(tenNightsTwoYearsAgoMini));
        c2.setBookingMiniDtoList(List.of(tenNightsThisYearMini));
        c3.setBookingMiniDtoList(List.of(fiveNightsThisYearMini));

        when(customerRepository.getReferenceById(tenNightsTwoYearsAgoDTO.getCustomerMiniDto().getId())).thenReturn(c1Customer);
        when(customerRepository.getReferenceById(tenNightsThisYearDTO.getCustomerMiniDto().getId())).thenReturn(c2Customer);
        when(customerRepository.getReferenceById(fiveNightsThisYearDTO.getCustomerMiniDto().getId())).thenReturn(c3Customer);

        when(bookingRepository.findAllByCustomer(c1Customer)).thenReturn(List.of(tenNightsTwoYearsAgoBooking));
        when(bookingRepository.findAllByCustomer(c2Customer)).thenReturn(List.of(tenNightsThisYearBooking));
        when(bookingRepository.findAllByCustomer(c3Customer)).thenReturn(List.of(fiveNightsThisYearBooking));

        assertFalse(discountService.tenOrMoreNightsThisYear(tenNightsTwoYearsAgoDTO));
        assertFalse(discountService.tenOrMoreNightsThisYear(fiveNightsThisYearDTO));
        assertTrue(discountService.tenOrMoreNightsThisYear(tenNightsThisYearDTO));

    }

    @Test
    void discountTestBothConditionsTrueTest() {
        BookingDetailedDto booking = new BookingDetailedDto(1L, LocalDate.now().minusYears(2),LocalDate.now().minusYears(2).plusDays(11),new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);

        when(discountServiceSpy.twoOrMoreNights(booking)).thenReturn(true);
        when(discountServiceSpy.tenOrMoreNightsThisYear(booking)).thenReturn(true);

        double discount = discountServiceSpy.discount(booking);
        assertEquals(0.975, discount, 0.001);
    }

    @Test
    void discountTestFirstConditionTrueTest() {

        BookingDetailedDto booking = new BookingDetailedDto(1L, LocalDate.now().minusYears(2),LocalDate.now().minusYears(2).plusDays(11),new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);

        when(discountServiceSpy.twoOrMoreNights(booking)).thenReturn(true);
        when(discountServiceSpy.tenOrMoreNightsThisYear(booking)).thenReturn(false);

        double discount = discountServiceSpy.discount(booking);
        assertEquals(0.995, discount, 0.001);
    }

    @Test
    void discountTestBothConditionsFalseTest() {
        BookingDetailedDto booking = new BookingDetailedDto(1L, LocalDate.now().minusYears(2),LocalDate.now().minusYears(2).plusDays(11),new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,100);
        when(discountServiceSpy.twoOrMoreNights(booking)).thenReturn(false);
        when(discountServiceSpy.tenOrMoreNightsThisYear(booking)).thenReturn(false);

        double discount = discountServiceSpy.discount(booking);
        assertEquals(1.0, discount, 0.001);
    }

    @Test
    void discountSundayToMondayTest() {
        LocalDate friday = LocalDate.of(2024,5,17);
        LocalDate sunday = LocalDate.of(2024,5,19);
        LocalDate monday = LocalDate.of(2024,5,20);

        BookingDetailedDto sundayToMonday = new BookingDetailedDto(1L, sunday,monday,new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);
        BookingDetailedDto fridayToSunday = new BookingDetailedDto(1L, friday,sunday,new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);
        BookingDetailedDto sundayToSundayThreeWeeksLater = new BookingDetailedDto(1L, sunday,sunday.plusWeeks(3),new CustomerMiniDto(), new RoomMiniDto(1L,0,100),0,0);

        double valueSundayToMonday = discountService.discountSundayToMonday(sundayToMonday);
        double valueFridayToSunday = discountService.discountSundayToMonday(fridayToSunday);
        double valueSundayToSundayThreeWeeksLater = discountService.discountSundayToMonday(sundayToSundayThreeWeeksLater);

        assertEquals(2d,valueSundayToMonday);
        assertEquals(0d,valueFridayToSunday);
        assertEquals(6d,valueSundayToSundayThreeWeeksLater);



    }
}