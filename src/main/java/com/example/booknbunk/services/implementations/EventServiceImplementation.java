package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.EventDto;
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

//testa
    @Override
    public List<EventDto> getAllEventsDtoByRoomId(Long id) {
        List<Event> events = eventRepository.findAllByRoomId(id);
        return events.stream()
                .map(this::eventToEventDto)
                .toList();
    }

//ok - ej testa
    private EventDto eventToEventDto(Event event) {
        EventDto.EventDtoBuilder dtoBuilder = EventDto.builder()
                .id(event.getId())
                .timeStamp(event.getTimeStamp())
                .roomNo(event.getRoom().getId());

        if (event instanceof EventRoomDoor) {
            EventRoomDoor doorEvent = (EventRoomDoor) event;
            dtoBuilder.doorEventType(doorEvent.getDoorEventType());

        } else if (event instanceof EventRoomCleaning) {
            EventRoomCleaning cleaningEvent = (EventRoomCleaning) event;
            dtoBuilder.cleaningStatus(cleaningEvent.getCleaningStatus())
                    .cleaningByUser(cleaningEvent.getCleaningByUser());
        }
        return dtoBuilder.build();
    }

//testa
    @Override
    public Event deserializeEvent(String message) {
        try {
            return objectMapper.readValue(message, Event.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error deserializing json string. " + e.getMessage());
            return null;
        }
    }

    //ej testa
    private Room getRoomFromEvent(Event event) {
        try {
            return roomRepository.findById(event.getRoom().getId()).get();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error finding room. " + e.getMessage());
            return null;
        }
    }

//ej testa
    private void handleEventRoomCleaning(EventRoomCleaning cleaningEvent, String message) {
        if (message.contains("RoomCleaningStarted")) {
            cleaningEvent.setCleaningStatus("Started");
        } else if (message.contains("RoomCleaningFinished")) {
            cleaningEvent.setCleaningStatus("Finished");
        }
    }

//ej testa
    private void handleEventRoomDoor(EventRoomDoor doorEvent, String message) {
        if (message.contains("RoomOpened")) {
            doorEvent.setDoorEventType("Opened");
        } else if (message.contains("RoomClosed")) {
            doorEvent.setDoorEventType("Closed");
        }
    }


//ej testa
    private Event prepareEvent(String message) {
        Event event = deserializeEvent(message);
        Room room = getRoomFromEvent(event);
        event.setRoom(room);
        return event;
    }


//ej testa
    private void processEventBasedOnType(Event event, String message) {
        if (event instanceof EventRoomDoor) {
            handleEventRoomDoor((EventRoomDoor) event, message);
        } else if (event instanceof EventRoomCleaning) {
            handleEventRoomCleaning((EventRoomCleaning) event, message);
        }
    }

    //testa
    @Transactional
    @Override
    public void processEvent(String message) {
        try {
            Event event = prepareEvent(message);
            processEventBasedOnType(event, message);
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing event " + e.getMessage());
        }
    }


}
