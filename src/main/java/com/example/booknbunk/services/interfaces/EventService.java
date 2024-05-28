package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.models.Event;

import java.util.List;

public interface EventService {


    List<EventDto> getAllEventsDtoByRoomId(Long id);

   // void saveEvent(Event event);
    void processEvent(String message);

    //EventDto eventToEventDto(Event event);
}
