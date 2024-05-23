package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.EventRoomCleaning;
import com.example.booknbunk.models.EventRoomDoor;
import com.example.booknbunk.models.Room;
import jakarta.transaction.Transactional;

import java.util.List;

public interface EventService {

    List<EventDto> getAllEventsDtoByRoomId(Long id);

    Event deserializeEvent(String message);


    //testa
    @Transactional
    void processEvent(String message);
}
