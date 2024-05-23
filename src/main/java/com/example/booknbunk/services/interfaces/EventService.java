package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.models.Event;

public interface EventService {

    EventDto eventToEventDto(Event event);

    void saveEvent(Event event);
    void processEvent(String message);
}
