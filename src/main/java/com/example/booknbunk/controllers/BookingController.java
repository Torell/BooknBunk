package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
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
    @PostMapping("/add")
    public String addBooking(Model model, BookingDetailedDto bookingDetailedDto){
        bookingService.createBooking(bookingDetailedDto);
        List<BookingDetailedDto> listOfBookings = bookingService.getAllBookingDetailedDto();
        model.addAttribute("allBookings", listOfBookings);
        return "redirect:/booking/getAll";
    }

    @RequestMapping("/createBooking")
    public String createBooking(Model model) {
        BookingDetailedDto booking = new BookingDetailedDto();
        RoomMiniDto roomMiniDto = new RoomMiniDto();
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto", roomMiniDto);
        return "addBooking";

    }

    @RequestMapping("/editBooking/{id}")
    public String editBooking(@PathVariable long id, Model model) {
        BookingDetailedDto booking = bookingService.findBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto");
        return "detailedBookingInfoAndEdit";
    }

    @RequestMapping("/modify")
    public String editBooking(BookingDetailedDto booking) {
        bookingService.modifyBooking(booking);
        return "redirect:/booking/getAll";
    }

    @PutMapping("/addBed/{number}")
    public int addBed(@RequestBody BookingDetailedDto bookingDetailedDto, @PathVariable int number) {
        return bookingService.extraBedSpaceAvailable(bookingDetailedDto, number);
    }

    @RequestMapping("/getAll")
    public String getAllBookings(Model model) {
        List<BookingDetailedDto> listOfBookings = bookingService.getAllBookingDetailedDto();
        model.addAttribute("allBookings", listOfBookings);
        model.addAttribute("bookingTitle", "All Bookings");
        return "allBookings";
    }

    @RequestMapping("/cancelBooking/{id}")
    public String deleteById(@PathVariable long id, Model model) {
        bookingService.cancelBooking(id);
        List<BookingDetailedDto> listOfBookings = bookingService.getAllBookingDetailedDto();
        model.addAttribute("allBookings", listOfBookings);
        model.addAttribute("bookingTitle", "All Bookings");
        return "redirect:/booking/getAll";
    }


}
