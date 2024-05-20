package com.example.booknbunk.controllers;


import com.example.booknbunk.services.interfaces.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;





}
