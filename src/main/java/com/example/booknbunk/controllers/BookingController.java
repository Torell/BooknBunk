package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.services.interfaces.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @RequestMapping("/{id}")
    public BookingDetailedDto findBookingById(@PathVariable long id) {
        return bookingService.findBookingById(id);
    }

    @RequestMapping("/listAll")
    public List<BookingDetailedDto> listAllBooking() {
       return bookingService.listALlBookings();
    }
    @PostMapping("/newBooking")
    public void createBooking(@RequestBody BookingDetailedDto bookingDetailedDto){
        bookingService.createBooking(bookingDetailedDto);
    }

    @PutMapping("/addBed/{number}")
    public int addBed(@RequestBody BookingDetailedDto bookingDetailedDto, @PathVariable int number) {
        return bookingService.addBed(bookingDetailedDto, number);
    }


}
