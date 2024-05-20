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
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + event.getRoom().getId() + " does not exist."));
        event.setRoom(room);
        eventRepository.save(event);
    }






    @Transactional
    @Override
    public void processEvent(String message) {
        try {
            Event event = objectMapper.readValue(message, Event.class);
            Room room = roomRepository.findById(event.getRoom().getId())
                    .orElseThrow(() -> new IllegalStateException("Room with ID: " + event.getRoom().getId() + " does not exist."));

            if (event instanceof EventRoomCleaning) {
                EventRoomCleaning cleaningEvent = (EventRoomCleaning) event;
                if (message.contains("RoomCleaningStarted")) {
                    cleaningEvent.setCleaningStatus("Started");
                } else if (message.contains("RoomCleaningFinished")) {
                    cleaningEvent.setCleaningStatus("Finished");
                }
            } else if (event instanceof EventRoomDoor) {
                EventRoomDoor roomEvent = (EventRoomDoor) event;
                if (message.contains("RoomOpened")) {
                    roomEvent.setDoorEventType("Opened");
                } else if (message.contains("RoomClosed")) {
                    roomEvent.setDoorEventType("Closed");
                }
            }

            event.setRoom(room);
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing event: " + e.getMessage());
        }
    }

/*
    private static final String ROOM_CLEANING_STARTED = "Cleaning Started";
    private static final String ROOM_CLEANING_FINISHED = "Cleaning Finished";
    private static final String ROOM_OPENED = "Room Opened";
    private static final String ROOM_CLOSED = "Room Closed";

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

    //deserialiserar
    private Event deserializeEvent(String message) {
        try {
            return objectMapper.readValue(message, Event.class);
        } catch (IOException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
        }
        return null;
    }

    //hittar händelsetyp
    private String getEventType(String message) {
        try {
            return objectMapper.readTree(message).get("type").asText();
        } catch (IOException e) {
            System.out.println("Error determining event type: " + e.getMessage());
            return null;
        }
    }


    //hämtar städare
    @Override
    public String getCleaningByUser(String message){
        try {
            if (message.contains("CleaningByUser")){
                return objectMapper.readTree(message).get("CleaningByUser").asText();
            }

        } catch (Exception e) {
            System.out.println("Error getting cleaner " + e.getMessage());
        }
        return null;
    }

    //hämtar rum
    @Override
    public String getRoom(String message){
        try {
            return objectMapper.readTree(message).get("RoomNo").asText();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting room no " + e.getMessage());
            return null;
        }
    }


    @Override
    public void mappEvent(String message) {
       Event event = deserializeEvent(message);
       String eventType = getEventType(message);
       String cleaningByUser = getCleaningByUser(message);
       String roomNo = getRoom(message);

        processEvent(event, eventType, cleaningByUser, roomNo);

        if (event != null) {
            Long room = event.getRoomNo();
            if (room != null){
                event.setRoom(room);

            } else {
                System.out.println("Room with id " + event.getRoom().getId() + " does not exist.");
            }
        } else {
            System.out.println("Failed to deserialize event.");
        }

    }


    @Transactional
    @Override
    public void processEvent(Event event, String eventType, String cleaningByUser, String roomNo) {
      try {
          switch (eventType) {
              case "EventRoomCleaningStarted":
                  event.setEventType(ROOM_CLEANING_STARTED);
                  ((EventRoomCleaning) event).setCleaningByUser(cleaningByUser);
                  break;
              case "RoomCleaningFinished":
                  event.setEventType(ROOM_CLEANING_FINISHED);
                  ((EventRoomCleaning) event).setCleaningByUser(cleaningByUser);
                  break;
              case "RoomOpened":
                  event.setEventType(ROOM_OPENED);
                  break;
              case "RoomClosed":
                  event.setEventType(ROOM_CLOSED);
                  break;
              default:
                  System.out.println("Unknown event type.");
                  break;
          }

          saveEvent(event);
      } catch (Exception e){
          e.printStackTrace();
          System.out.println("Error processing event. " + e.getMessage());
      }

    }


    @Transactional
    @Override
    public void saveEvent(Event event) {
        try {
            eventRepository.save(event);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving event. " + e.getMessage());
        }
    }
*/

}

