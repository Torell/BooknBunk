package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.EventRoomCleaning;
import com.example.booknbunk.models.EventRoomDoor;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.example.booknbunk.services.interfaces.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImplementation implements EventService {

    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    public EventServiceImplementation(EventRepository eventRepository, RoomRepository roomRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Transactional
    @Override
    public void saveEvent(Event event) {
        Room room = roomRepository.findById(event.getRoom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + event.getRoom().getId()
                        + " does not exist."));
        event.setRoom(room);
        eventRepository.save(event);
    }



    @Transactional
    @Override
    public void mappEvent(String message) {
        try {
            Event event = objectMapper.readValue(message, Event.class);
            Room room = roomRepository.findById(event.getRoom().getId())
                    .orElseThrow(() -> new IllegalStateException("Room with ID: " + event.getRoom().getId() + " does not exist."));
            event.setRoom(room);
           // eventRepository.save(event);
            processEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing event: " + e.getMessage());
        }

    }

    @Transactional
    @Override
    public void processEvent(Event event) {
        if (event instanceof EventRoomCleaning) {

        } else if (event instanceof EventRoomDoor) {
            // Hantera dörr-event
        }
        eventRepository.save(event);
    }

}
