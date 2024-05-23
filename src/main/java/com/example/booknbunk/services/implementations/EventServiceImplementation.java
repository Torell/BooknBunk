package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.models.*;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.example.booknbunk.services.interfaces.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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


    @Override
    public List<EventDto> getAllEventsDtoByRoomId(Long id) {
        List<Event> events = eventRepository.findAllByRoomId(id);
        return events.stream()
                .map(this::eventToEventDto)
                .toList();
    }


    private EventDto eventToEventDto(Event event) {
        EventDto.EventDtoBuilder dtoBuilder = EventDto.builder()
                .id(event.getId())
                .timeStamp(event.getTimeStamp())
                .roomNo(event.getRoom().getId())
                .type(event.getClass().getSimpleName());


        if (event instanceof EventRoomOpened ){
            EventRoomOpened eventRoomOpened = (EventRoomOpened) event;

        } else if (event instanceof EventRoomClosed){
            EventRoomClosed eventRoomClosed = (EventRoomClosed) event;

        } else if (event instanceof EventRoomCleaningStarted){
            EventRoomCleaningStarted eventRoomCleaningStarted = (EventRoomCleaningStarted) event;
            dtoBuilder.cleaningByUser(eventRoomCleaningStarted.getCleaningByUser());

        } else if (event instanceof EventRoomCleaningFinished) {
            EventRoomCleaningFinished eventRoomCleaningFinished = (EventRoomCleaningFinished) event;
            dtoBuilder.cleaningByUser(eventRoomCleaningFinished.getCleaningByUser());

        }
        return dtoBuilder.build();
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
    public void processEvent(String message) {
        try {
            Event event = objectMapper.readValue(message, Event.class);
            saveEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing event: " + e.getMessage());
        }

    }
}
