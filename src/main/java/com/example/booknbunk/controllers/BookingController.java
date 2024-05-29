package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.services.implementations.EmailServiceImplementation;
import com.example.booknbunk.services.interfaces.BookingService;
import com.example.booknbunk.services.interfaces.CustomerService;
import com.example.booknbunk.services.interfaces.MyUserService;
import com.example.booknbunk.services.interfaces.RoomService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Book;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerService customerService;
    private final EmailServiceImplementation emailService;
    private final MyUserService myUserService;
    private final CustomerRepository customerRepository;


    @RequestMapping("/{id}")
    public BookingDetailedDto findBookingById(@PathVariable long id) {
        return bookingService.findBookingById(id);
    }

    @RequestMapping("/listAll")
    public List<BookingDetailedDto> listAllBooking() {
       return bookingService.getAllBookingDetailedDto();
    }


    @PostMapping("/add")
    public String addBooking(Model model, BookingDetailedDto bookingDetailedDto, RedirectAttributes redirectAttributes, CustomerDetailedDto customerDetailedDto) throws MessagingException {
        RoomDetailedDto desiredRoom = roomService.findRoomById(bookingDetailedDto.getRoomMiniDto().getId());
        StringBuilder returnMessage = bookingService.createOrChangeBooking(bookingDetailedDto,desiredRoom);
        redirectAttributes.addFlashAttribute("returnMessage",returnMessage);
        model.addAttribute("booking",bookingDetailedDto);
            return "redirect:/booking/createBooking";
    }

    @RequestMapping("/findAvailability")
    public String findAvailability(BookingDetailedDto booking, Model model) {
        model.addAttribute("booking",booking);
        return "/booking/findAvailability";
    }
    @RequestMapping("/getAvailability")
    public String getAvailability(Model model, BookingDetailedDto booking,@RequestParam(name = "occupants") int occupants) {

        model.addAttribute("availableRoom",bookingService.getAllAvailabileRoomsBasedOnRoomSizeAndDateIntervall(occupants,booking));
        model.addAttribute("headline","Available Rooms");
        model.addAttribute("booking",booking);

        return "/booking/showAvailability";
    }

    @RequestMapping("/createBooking")
    public String createBooking(Model model) {
        BookingDetailedDto booking = new BookingDetailedDto();
        RoomMiniDto roomMiniDto = new RoomMiniDto();
        model.addAttribute("defaultRoomId",1L);
        model.addAttribute("rooms",roomService.getAllRoomsMiniDto());
        model.addAttribute("customers",customerService.getAllCustomersDetailedDto());
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto", roomMiniDto);
        return "/booking/addBooking";

    }

    @PostMapping("/createBookingFromAvailableRoom")
    public String createBookingFromAvailableRoom(Model model, @ModelAttribute BookingDetailedDto booking) {
        RoomMiniDto roomMiniDto = new RoomMiniDto();
        model.addAttribute("rooms", roomService.getAllRoomsMiniDto());
        model.addAttribute("customers", customerService.getAllCustomersDetailedDto());
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto", roomMiniDto);
        return "booking/addBooking";
    }

    @RequestMapping("/editBooking/{id}")
    public String editBooking(@PathVariable long id, Model model,BookingDetailedDto booking) {
        booking = bookingService.findBookingById(id);
        RoomMiniDto roomMiniDto = new RoomMiniDto();
        model.addAttribute("rooms",roomService.getAllRoomsMiniDto());
        model.addAttribute("booking", booking);
        model.addAttribute("roomMiniDto", roomMiniDto);
        return "/booking/detailedBookingInfoAndEdit";
    }

    @RequestMapping("/modify")
    public String editBooking(BookingDetailedDto bookingDetailedDto,Model model, RedirectAttributes redirectAttributes) throws MessagingException {

        RoomDetailedDto desiredRoom = roomService.findRoomById(bookingDetailedDto.getRoomMiniDto().getId());
        StringBuilder returnMessage = bookingService.createOrChangeBooking(bookingDetailedDto,desiredRoom);

        redirectAttributes.addFlashAttribute("returnMessage",returnMessage);
        redirectAttributes.addFlashAttribute("booking",bookingDetailedDto);
        model.addAttribute("booking",bookingDetailedDto);

        return "redirect:/booking/editBooking/" + bookingDetailedDto.getId();


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
