package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.services.interfaces.BookingService;
import com.example.booknbunk.services.interfaces.RoomService;
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
    private final RoomService roomService;
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

        if (bookingService.extraBedSpaceAvailable(bookingDetailedDto)
                && bookingService.compareDesiredDatesToBookedDates(bookingDetailedDto, roomService.findRoomById(bookingDetailedDto.getRoomMiniDto().getId()))
        && bookingService.startDateIsBeforeEndDate(bookingDetailedDto)) {
            bookingService.createBooking(bookingDetailedDto);
            List<BookingDetailedDto> listOfBookings = bookingService.getAllBookingDetailedDto();
            model.addAttribute("allBookings", listOfBookings);

            return "redirect:/booking/getAll";
        }
        else return "/booking/notEnoughSpaceWarning";
    }

    @RequestMapping("/findAvailability")
    public String findAvailability() {
        return "/booking/findAvailability";
    }
    @RequestMapping("/getAvailability")
    public String getAvailability(Model model, @RequestParam(name = "occupants") int occupants, @RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) {

        model.addAttribute("availableRoom",bookingService.getAvailabilityBasedOnRoomSizeAndDateIntervall(occupants,startDate,endDate));
        model.addAttribute("headline","Available Rooms");

        return "/booking/showAvailability";
    }

    @RequestMapping("/createBooking")
    public String createBooking(Model model) {
        BookingDetailedDto booking = new BookingDetailedDto();
        RoomMiniDto roomMiniDto = new RoomMiniDto();
        model.addAttribute("defaultRoomId",1L);
        model.addAttribute("rooms",roomService.getAllRoomsMiniDto());
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto", roomMiniDto);
        return "/booking/addBooking";

    }

    @RequestMapping("/editBooking/{id}")
    public String editBooking(@PathVariable long id, Model model) {
        BookingDetailedDto booking = bookingService.findBookingById(id);
        model.addAttribute("rooms",roomService.getAllRoomsMiniDto());
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto");
        return "/booking/detailedBookingInfoAndEdit";
    }

    @RequestMapping("/modify")
    public String editBooking(BookingDetailedDto booking) {
        if (bookingService.extraBedSpaceAvailable(booking)) {
            bookingService.modifyBooking(booking);
            return "redirect:/booking/getAll";
        } else return "/booking/notEnoughSpaceWarning";
    }




    @RequestMapping("/getAll")
    public String getAllBookings(Model model) {
        List<BookingDetailedDto> listOfBookings = bookingService.getAllBookingDetailedDto();
        model.addAttribute("allBookings", listOfBookings);
        model.addAttribute("bookingTitle", "All Bookings");
        return "/booking/allBookings";
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
