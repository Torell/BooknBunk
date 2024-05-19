package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.CustomerMiniDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.example.booknbunk.services.interfaces.BookingService;
import com.example.booknbunk.services.interfaces.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookingRepository mockBookingRepo;
    @MockBean
    RoomRepository mockRoomRepo;
    @MockBean
    private BookingService bookingService;
    @MockBean
    RoomService roomService;
    @BeforeEach
    public void init() {
        RoomMiniDto r1 = new RoomMiniDto(1L,0,100);
        RoomMiniDto r2 = new RoomMiniDto(2L,1,100);
        RoomMiniDto r3 = new RoomMiniDto(3L,2,100);
        RoomMiniDto r4 = new RoomMiniDto(4L,3,100);

        CustomerMiniDto c1 = new CustomerMiniDto(1L,"Rasmus");
        CustomerMiniDto c2 = new CustomerMiniDto(2L, "Stephanie");
        CustomerMiniDto c3 = new CustomerMiniDto(3L,"Anna");



        BookingDetailedDto booking1 = new BookingDetailedDto(1L,LocalDate.of(2024,5,7),LocalDate.of(2024,5,8),
                c1, r1, 0,0);
        BookingDetailedDto booking2 = new BookingDetailedDto(2L,LocalDate.of(2024,5,7),LocalDate.of(2024,5,8),
                c2, r3, 0,0);
        BookingDetailedDto booking3 = new BookingDetailedDto(3L,LocalDate.of(2024,5,9),LocalDate.of(2024,5,11),
                c3, r4, 2,0);
        BookingDetailedDto booking4 = new BookingDetailedDto(4L,LocalDate.of(2024,6,7),LocalDate.of(2024,6,8),
                c2, r2, 1,0);
        BookingDetailedDto booking5 = new BookingDetailedDto(5L,LocalDate.of(2024,7,7),LocalDate.of(2024,7,8),
                c3, r1, 0,0);

        when(bookingService.findBookingById(1L)).thenReturn(Optional.of(booking1).orElse(null));
        when(bookingService.findBookingById(2L)).thenReturn(Optional.of(booking1).orElse(null));
        when(bookingService.findBookingById(3L)).thenReturn(Optional.of(booking1).orElse(null));
        when(bookingService.findBookingById(4L)).thenReturn(Optional.of(booking1).orElse(null));
        when(bookingService.findBookingById(5L)).thenReturn(Optional.of(booking1).orElse(null));

        when(bookingService.getAllBookingDetailedDto()).thenReturn(Arrays.asList(booking1, booking2, booking3, booking4, booking5));

    }
    @Test
    void findBookingById() {
    }

    @Test
    void listAllBooking() {
    }

    @Test
    void addBooking() throws Exception {
        BookingDetailedDto bookingDto = BookingDetailedDto.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .roomMiniDto(new RoomMiniDto(1L, 2,0))
                .build();

        // Execute & Assert
        mockMvc.perform(post("/booking/add")
                        .flashAttr("bookingDetailedDto", bookingDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/booking/createBooking"));
    }



    @Test
    void findAvailability() {
    }

    @Test
    void getAvailability() {
    }

    @Test
    void createBooking() {
    }

    @Test
    void editBooking() {
    }

    @Test
    void testEditBooking() {
    }

    @Test
    void getAllBookings() {
    }

    @Test
    void deleteById() {
    }
}