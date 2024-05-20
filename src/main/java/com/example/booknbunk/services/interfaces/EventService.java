package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.EventRoomCleaning;
import com.example.booknbunk.models.EventRoomDoor;
import com.example.booknbunk.models.Room;
import jakarta.transaction.Transactional;

public interface EventService {


    Event deserializeEvent(String message);

    @Transactional
    Room getRoomFromEvent(Event event);

    void handleEventRoomCleaning(EventRoomCleaning cleaningEvent, String message);

    void handleEventRoomDoor(EventRoomDoor doorEvent, String message);

    @Transactional
    Event prepareEvent(String message);

    void processEventBasedOnType(Event event, String message);

    @Transactional
    void processEvent(String message);

}
