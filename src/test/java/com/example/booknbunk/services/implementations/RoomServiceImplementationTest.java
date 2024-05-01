package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.BookingMiniDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoomServiceImplementationTest {

    @Mock
    private RoomRepository roomRepo;

    @Mock
    private BookingRepository bookingRepo;

    @Mock
    private CustomerRepository customerRepo;


    private RoomServiceImplementation service =new RoomServiceImplementation(roomRepo, bookingRepo, customerRepo);

    long roomId = 1L;
    int roomSize = 2;
    List<Booking> bookings = new ArrayList<>();

    List<BookingMiniDto> bookingMiniDtoList = new ArrayList<>();
    Room mockRoom = new Room(roomId, roomSize, bookings);

    RoomDetailedDto roomDetailedDto = RoomDetailedDto.builder()
            .id(1L)
            .roomSize(2)
            .bookingMiniDtoList(new ArrayList<>())
            .build();

    RoomMiniDto roomMiniDto = RoomMiniDto.builder()
            .id(1L)
            .roomSize(2)
            .build();




    @Test
    void roomToRoomDetailedDto() {
        RoomDetailedDto actual = service.roomToRoomDetailedDto(mockRoom);
        assertEquals(actual.getId(), roomDetailedDto.getId());
        assertEquals(actual.getRoomSize(), roomDetailedDto.getRoomSize());
        assertEquals(actual.getBookingMiniDtoList(), roomDetailedDto.getBookingMiniDtoList());

    }

    @Test
    void roomDetailedDtoToRoom() {
        Room actual = service.roomDetailedDtoToRoom(roomDetailedDto);
        assertEquals(actual.getId(), mockRoom.getId());
        assertEquals(actual.getRoomSize(), mockRoom.getRoomSize());
        assertIterableEquals(actual.getBookings(), mockRoom.getBookings());

    }

    @Test
    void roomToRoomMiniDto() {
        RoomMiniDto actual = service.roomToRoomMiniDto(mockRoom);
        assertEquals(actual.getId(), roomMiniDto.getId());
        assertEquals(actual.getRoomSize(), roomMiniDto.getRoomSize());
    }

    @Test
    void roomMiniDtoToRoom() {
        Room actual = service.roomMiniDtoToRoom(roomMiniDto);
        assertEquals(actual.getId(), mockRoom.getId());
        assertEquals(actual.getRoomSize(), mockRoom.getRoomSize());
    }


    @Test
    void getAllRoomsMiniDto() {
        List<Room> rooms = Arrays.asList(new Room(1L, 2), new Room(2L, 3));
        when(roomRepo.findAll()).thenReturn(rooms);
        RoomServiceImplementation service = new RoomServiceImplementation(roomRepo, bookingRepo, customerRepo);
        List<RoomMiniDto> actual = service.getAllRoomsMiniDto();
        assertEquals(rooms.size(), actual.size());
    }

    @Test
    void findRoomById() {
        RoomServiceImplementation service2 = new RoomServiceImplementation(roomRepo, bookingRepo, customerRepo);
     when(roomRepo.findById(roomId)).thenReturn(Optional.of(mockRoom));
     RoomDetailedDto foundRoom = service2.findRoomById(mockRoom.getId());
     verify(roomRepo, times(1)).findById(mockRoom.getId());
     assertEquals(mockRoom.getId(), foundRoom.getId());

    }



}