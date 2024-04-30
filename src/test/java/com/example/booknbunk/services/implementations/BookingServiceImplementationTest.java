package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.*;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookingServiceImplementationTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private RoomRepository roomRepository;

    private final BookingServiceImplementation bookingService = new BookingServiceImplementation(bookingRepository,roomRepository);


    private final Long id = 1L;
    private final int roomSize = 1;
    private final String name = "testName";

    private final String email = "test@name.com";
    private final LocalDate today = LocalDate.now();
    private final LocalDate tomorrow = LocalDate.now().plusDays(1);
    private final LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2);
    private final int extraBed = 1;

    private final Room room = new Room(id,roomSize,null);
    private final RoomMiniDto roomMiniDto = new RoomMiniDto(id,roomSize);

    private final RoomDetailedDto roomDetailedDto = new RoomDetailedDto(id,roomSize,new ArrayList<>());
    private final Customer customer = new Customer(id,name,email,null);
    private final CustomerMiniDto customerMiniDto = new CustomerMiniDto(id,name);
    private final BookingDetailedDto bookingDetailedDto = new BookingDetailedDto(id,today,tomorrow,customerMiniDto, roomMiniDto,extraBed);
    private final BookingMiniDto bookingMiniDto = new BookingMiniDto(id,today,tomorrow);
    private final Booking booking = new Booking(id,today,tomorrow,room,roomSize,customer);


    @Test
    void bookingToBookingDetailedDto() {
        BookingDetailedDto actual = bookingService.bookingToBookingDetailedDto(booking);

        assertEquals(actual.getId(), bookingDetailedDto.getId());
        assertEquals(actual.getExtraBed(), bookingDetailedDto.getExtraBed());
        assertEquals(actual.getStartDate(), bookingDetailedDto.getStartDate());
        assertEquals(actual.getRoomMiniDto(), bookingDetailedDto.getRoomMiniDto());
        assertEquals(actual.getCustomerMiniDto(), bookingDetailedDto.getCustomerMiniDto());

    }

    @Test
    void bookingToBookingMiniDto() {
        BookingMiniDto actual = bookingService.bookingToBookingMiniDto(booking);

        assertEquals(bookingMiniDto.getId(), actual.getId());
        assertEquals(bookingMiniDto.getStartDate(), actual.getStartDate());
        assertEquals(bookingMiniDto.getEndDate(), actual.getEndDate());
    }

    @Test
    void bookingDetailedDtoToBooking() {
        Booking actual = bookingService.bookingDetailedDtoToBooking(bookingDetailedDto);

        assertEquals(booking.getId(), actual.getId());
        assertEquals(booking.getStartDate(), actual.getStartDate());
        assertEquals(booking.getEndDate(), actual.getEndDate());
    }

    @Test
    void roomMiniDtoRoom() {
        RoomMiniDto actual = bookingService.roomToRoomMiniDto(room);

        assertEquals(roomMiniDto.getId(), actual.getId());
        assertEquals(roomMiniDto.getRoomSize(), actual.getRoomSize());
    }


    @Test
    void customerToCustomerMiniDto() {
        CustomerMiniDto actual = bookingService.customerToCustomerMiniDto(customer);

        assertEquals(customerMiniDto.getId(), actual.getId());
        assertEquals(customerMiniDto.getName(), actual.getName());
    }

    @Test
    void roomToRoomMiniDto() {
        Room actual = bookingService.roomMiniDtoRoom(roomMiniDto);

        assertEquals(room.getId(), actual.getId());
        assertEquals(room.getRoomSize(), actual.getRoomSize());
    }

    @Test
    void customerMiniDtoToCustomer() {
        Customer actual = bookingService.customerMiniDtoToCustomer(customerMiniDto);

        assertEquals(customer.getId(), actual.getId());
        assertEquals(customer.getName(), actual.getName());
    }


    @Test
    void findBookingById() {
        BookingServiceImplementation bookingService2 = new BookingServiceImplementation(bookingRepository,roomRepository);
        when(bookingRepository.getReferenceById(id)).thenReturn(booking);
        BookingDetailedDto actual = bookingService2.findBookingById(id);

        assertEquals(bookingDetailedDto.getId(), actual.getId());
        assertEquals(bookingDetailedDto.getEndDate(), actual.getEndDate());
        assertEquals(bookingDetailedDto.getStartDate(), actual.getStartDate());
        assertEquals(bookingDetailedDto.getExtraBed(), actual.getExtraBed());
        assertEquals(bookingDetailedDto.getCustomerMiniDto(), actual.getCustomerMiniDto());
    }




    @Test
    void getAvailabilityBasedOnRoomSizeAndDateIntervall() {
        BookingMiniDto bookingMiniDto = new BookingMiniDto(id,today,tomorrow);

        RoomDetailedDto roomDetailedDto0 = new RoomDetailedDto(1L,0,List.of(bookingMiniDto));
        RoomDetailedDto roomDetailedDto1 = new RoomDetailedDto(2L,1,new ArrayList<>());
        RoomDetailedDto roomDetailedDto2 = new RoomDetailedDto(3L,2,new ArrayList<>());
        RoomDetailedDto roomDetailedDto3 = new RoomDetailedDto(4L,3,new ArrayList<>());

        Room room0 = new Room(1L,0, new ArrayList<>());
        Room room1 = new Room(2L,1, new ArrayList<>());
        Room room2 = new Room(3L,2, new ArrayList<>());
        Room room3 = new Room(4L,3, new ArrayList<>());

        Booking booking1 = new Booking(id,today,tomorrow,room0,0,customer);
        room0.setBookings(List.of(booking1));

        List<RoomDetailedDto> expected1 = Arrays.asList(roomDetailedDto1,roomDetailedDto2,roomDetailedDto3);
        List<RoomDetailedDto> expected2 = Arrays.asList(roomDetailedDto0,roomDetailedDto1,roomDetailedDto2,roomDetailedDto3);

        when(roomRepository.findAll()).thenReturn(Arrays.asList(room0,room1,room2,room3));
        BookingServiceImplementation bookingService2 = new BookingServiceImplementation(bookingRepository,roomRepository);

        List<RoomDetailedDto> actual1 = bookingService2.getAllAvailabileRoomsBasedOnRoomSizeAndDateIntervall(0,today.toString(),tomorrow.toString());
        List<RoomDetailedDto> actual2 = bookingService2.getAllAvailabileRoomsBasedOnRoomSizeAndDateIntervall(0,dayAfterTomorrow.toString(),dayAfterTomorrow.toString());

        assertEquals(expected1.size(), actual1.size());
        assertEquals(expected2.size(),actual2.size());
        assertIterableEquals(expected1,actual1);
        assertIterableEquals(expected2,actual2);
    }

    @Test
    void extraBedSpaceAvailable() {
        BookingDetailedDto falseBooking = new BookingDetailedDto(id,today,tomorrow,customerMiniDto,roomMiniDto,5);
        assertTrue(bookingService.extraBedSpaceAvailable(bookingDetailedDto));
        assertFalse(bookingService.extraBedSpaceAvailable(falseBooking));
    }

    @Test
    void getAllBookingDetailedDto() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));
        BookingServiceImplementation bookingService2 = new BookingServiceImplementation(bookingRepository,roomRepository);

        List<BookingDetailedDto> expected = List.of(bookingDetailedDto);
        List<BookingDetailedDto> actual = bookingService2.getAllBookingDetailedDto();

        assertIterableEquals(expected,actual);
    }


    @Test
    void getAllDatesBetweenStartAndEndDate() {
        List<LocalDate> expectedDates = new ArrayList<>();
        expectedDates.add(today);
        expectedDates.add(tomorrow);
        expectedDates.add(dayAfterTomorrow);

        List<LocalDate> actualDates = bookingService.getAllDatesBetweenStartAndEndDate(today,dayAfterTomorrow);
        assertIterableEquals(expectedDates,actualDates);
    }

    @Test
    void compareDesiredDatesToBookedDates() {
        BookingDetailedDto startDateIsBeforeToday = new BookingDetailedDto(id,today.minusDays(1),tomorrow,customerMiniDto,roomMiniDto,1);

        RoomDetailedDto bookedRoom = new RoomDetailedDto(id,1,List.of(new BookingMiniDto(id,today,tomorrow)));

        assertTrue(bookingService.compareDesiredDatesToBookedDates(bookingDetailedDto,roomDetailedDto));
        assertFalse(bookingService.compareDesiredDatesToBookedDates(startDateIsBeforeToday,roomDetailedDto));
        assertFalse(bookingService.compareDesiredDatesToBookedDates(bookingDetailedDto,bookedRoom));
    }

    @Test
    void startDateIsBeforeEndDate() {
        BookingDetailedDto wrongStartDateBooking = new BookingDetailedDto(id,tomorrow,today,customerMiniDto,roomMiniDto,0);
        assertTrue(bookingService.startDateIsBeforeEndDate(bookingDetailedDto));
        assertFalse(bookingService.startDateIsBeforeEndDate(wrongStartDateBooking));
    }
}