package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.services.interfaces.RoomService;

public class RoomServiceImplementation implements RoomService {


    @Override
    public Room roomDetailedDtoToRoom(RoomDetailedDto roomDetailedDto){
        return Room.builder()
                .id(roomDetailedDto.getId())
                .roomSize(roomDetailedDto.getRoomSize())
                .bookings(roomDetailedDto.getBookingMiniDtoList()
                        .stream().map(bookingMiniDto -> bookingMiniDtoToBooking(bookingMiniDto))
                        .toList()).build();

    }

    @Override
    public RoomDetailedDto roomToRoomDetailedDto(Room room){
        return RoomDetailedDto.builder()
                .id(room.getId())
                .RoomSize(room.getRoomSize())
                .bookingMiniDtoList(room.getBookings()
                        .stream().map(booking -> bookingToBookingMiniDto(booking))
                        .toList()).build();
    }

    @Override
    public RoomMiniDto roomToRoomMiniDto(Room room) {
        return RoomMiniDto.builder()
                .id(room.getId())
                .roomSize(room.getRoomSize())
                .build();
    }

    @Override
    public Room roomMiniDtoToRoom(RoomMiniDto roomMiniDto) {
        return Room.builder()
                .id(roomMiniDto.getId())
                .roomSize(roomMiniDto.getRoomSize())
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
