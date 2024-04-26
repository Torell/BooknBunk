package com.example.booknbunk.services.interfaces;
import com.example.booknbunk.dtos.*;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;
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

    int extraBedSpaceAvailable(BookingDetailedDto bookingDetailedDto, int number);
    public List<BookingDetailedDto> getAllBookingDetailedDto();

    public void cancelBooking(long id);

    public void modifyBooking(BookingDetailedDto bookingDetailedDto);

    public int changePeriod(BookingDetailedDto bookingDetailedDto, String startDate, String endDate);


}
