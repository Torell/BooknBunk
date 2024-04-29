package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;

import java.util.List;

public interface RoomService {


    RoomDetailedDto roomToRoomDetailedDto(Room room);
    Room roomDetailedDtoToRoom(RoomDetailedDto roomDetailedDto);

    RoomMiniDto roomToRoomMiniDto(Room room);
    Room roomMiniDtoToRoom(RoomMiniDto roomMiniDto);

    Booking bookingMiniDtoToBooking(BookingMiniDto bookingMiniDto);
    BookingMiniDto bookingToBookingMiniDto(Booking booking);

    RoomDetailedDto findRoomById(long id);

    List<RoomMiniDto> getAllRoomsMiniDto();
}
