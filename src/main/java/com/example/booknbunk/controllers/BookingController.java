package com.example.booknbunk.controllers;

import com.example.booknbunk.repositories.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;


}
