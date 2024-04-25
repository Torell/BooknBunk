package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.models.Booking;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.services.interfaces.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
       return bookingService.getAllBookingDetailedDto();
    }
    @PostMapping("/newBooking")
    public void createBooking(@RequestBody BookingDetailedDto bookingDetailedDto){
        bookingService.createBooking(bookingDetailedDto);
    }

    @PutMapping("/addBed/{number}")
    public int addBed(@RequestBody BookingDetailedDto bookingDetailedDto, @PathVariable int number) {
        return bookingService.addBed(bookingDetailedDto, number);
    }

    @RequestMapping("/getAll")
    public String getAllBookings(Model model) {
        List<BookingDetailedDto> listOfBookings = bookingService.getAllBookingDetailedDto();
        model.addAttribute("allBookings", listOfBookings);
        model.addAttribute("bookingTitle", "All Bookings");
        return "allBookings";
    }


}
