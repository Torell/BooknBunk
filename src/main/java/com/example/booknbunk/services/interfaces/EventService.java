package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.Room;
import jakarta.transaction.Transactional;

public interface EventService {
    @Transactional
    void saveEvent(Event event);

    @Transactional
    void processEvent(String message);
/*
    @Transactional
    void mappEvent(String message);

    String getCleaningByUser(String message);

    String getRoom(String message);


    @Transactional
    void processEvent(Event event, String eventType, String cleaningByUser, String roomNo);

    @Transactional
    void saveEvent(Event event);*/
}
