package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.models.Event;
import jakarta.transaction.Transactional;

public interface EventService {

    @Transactional
    void saveEvent(Event event);

    @Transactional
    void mappEvent(String message);

    @Transactional
    void processEvent(Event event);
}
