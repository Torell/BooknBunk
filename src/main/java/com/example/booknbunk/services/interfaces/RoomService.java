package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;

public interface RoomService {


    //här ska vi omvandla detailedDTO till room.den har en miniDTOBooking som ska bli en lista
    Room roomDetailedDtoToRoom(RoomDetailedDto roomDetailedDto);

    Room roomMiniDtoToRoom(RoomMiniDto roomMiniDto);
    RoomMiniDto roomToRoomMiniDto(Room room);

    Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto);
    BookingMiniDto bookingToBookingMiniDto(Booking booking);

}
