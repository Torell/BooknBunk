package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.models.Event;

public interface EventService {

    void saveEvent(Event event);
    void processEvent(String message);
}
