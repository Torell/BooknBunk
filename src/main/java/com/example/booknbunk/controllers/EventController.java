package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.services.interfaces.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/rooms/{id}/events")
    public String showEventsForRoom(@PathVariable Long id, Model model){
        List<EventDto> events = eventService.getAllEventsDtoByRoomId(id);
        model.addAttribute("events", events);
        return "/room/roomEvents";
    }

}
