package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.*;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;
import jakarta.mail.MessagingException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {


    //Conversion
    BookingDetailedDto bookingToBookingDetailedDto(Booking booking);
    BookingMiniDto bookingToBookingMiniDto(Booking booking);
    Booking bookingDetailedDtoToBooking(BookingDetailedDto bookingDetailedDto);

    Room roomMiniDtoRoom(RoomMiniDto roomMiniDto);

    CustomerMiniDto customerToCustomerMiniDto(Customer customer);
    Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto);
    RoomMiniDto roomToRoomMiniDto(Room room);


    //methods

    BookingDetailedDto findBookingById(long id);

    StringBuilder createOrChangeBooking(BookingDetailedDto bookingDetailedDto, RoomDetailedDto roomDetailedDto) throws MessagingException;

    boolean extraBedSpaceAvailable(BookingDetailedDto bookingDetailedDto);

    List<LocalDate> getAllDatesBetweenStartAndEndDate(LocalDate startDate,LocalDate endDate);

    public boolean checkRoomForAvailability(BookingDetailedDto booking, RoomDetailedDto room);
    List<BookingDetailedDto> getAllBookingDetailedDto();

    void cancelBooking(long id);


    void modifyBooking(BookingDetailedDto bookingDetailedDto);

    List<RoomDetailedDto> getAllAvailabileRoomsBasedOnRoomSizeAndDateIntervall(int occupants, BookingDetailedDto bookingDetailedDto);

    List<RoomDetailedDto> getAllRooms();
    public RoomDetailedDto roomToRoomDetailedDto(Room room);

    public boolean startDateIsBeforeEndDate(BookingDetailedDto booking);


    double calculateTotalPrice(BookingDetailedDto bookingDetailedDto);
}
