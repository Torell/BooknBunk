package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.*;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {


    //Conversion
    BookingDetailedDto bookingToBookingdetailedDto(Booking booking);
    BookingMiniDto bookingToBookingMiniDto(Booking booking);
    Booking bookingDetailedDtoToBooking(BookingDetailedDto bookingDetailedDto);

    Room roomMiniDtoRoom(RoomMiniDto roomMiniDto);

    Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto);
    CustomerMiniDto customerToCustomerMiniDto(Customer customer);
    Customer customerMiniDtoToCustomer(CustomerMiniDto customerMiniDto);
    RoomMiniDto roomToRoomMiniDto(Room room);


    //methods

    BookingDetailedDto findBookingById(long id);

    void createBooking(BookingDetailedDto bookingDetailedDto);

    boolean extraBedSpaceAvailable(BookingDetailedDto bookingDetailedDto);

    List<LocalDate> getAllDatesBetweenStartAndEndDate(LocalDate startDate,LocalDate endDate);

    public boolean compareDesiredDatesToBookedDates(BookingDetailedDto booking, RoomDetailedDto room);
    List<BookingDetailedDto> getAllBookingDetailedDto();

    void cancelBooking(long id);

    void modifyBooking(BookingDetailedDto bookingDetailedDto);

    int changePeriod(BookingDetailedDto bookingDetailedDto, String startDate, String endDate);


}
