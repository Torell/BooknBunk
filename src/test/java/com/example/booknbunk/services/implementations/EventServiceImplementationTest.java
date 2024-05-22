package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.EventRoomCleaning;
import com.example.booknbunk.models.EventRoomDoor;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class EventServiceImplementationTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EventServiceImplementation eventServiceImplementation;

    @Mock
    private EventRepository eventRepository;
    @Mock
    private RoomRepository roomRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  /*  @Test
    void processEvent_Successful() {
        // Skapa en mock för Event och Room
        Event eventMock = mock(Event.class);
        Room roomMock = mock(Room.class);

        // Mocka beteendet för getRoom() för att returnera roomMock
        when(eventMock.getRoom()).thenReturn(roomMock);

        // Skapa en mock för eventRepository
        EventRepository eventRepositoryMock = mock(EventRepository.class);

        // Skapa en instans av EventServiceImplementation och sätt mockarna
        EventServiceImplementation eventService = new EventServiceImplementation();
        eventService.setEventRepository(eventRepositoryMock);

        // Utför test
        eventService.processEvent(eventMock);

        // Verifiera att eventRepository.save() har anropats med rätt argument
        verify(eventRepositoryMock, times(1)).save(any(EventRoomDoor.class));
    }*/



    @Test
    void processEvent_DeserializationError() throws JsonProcessingException {
        // Arrange
        String message = "Invalid JSON message";
        when(objectMapper.readValue(message, Event.class)).thenThrow(new RuntimeException("Deserialization error"));

        // Act
        eventServiceImplementation.processEvent(message);

        // Assert
        // Verify that the eventRepository's save method was not called
        verify(eventRepository, never()).save(any());
    }

    @Test
    void processEvent_RoomNotFoundError() throws JsonProcessingException {
        // Arrange
        String message = "{\"timeStamp\":\"2024-05-22T12:00:00\",\"room\":{\"id\":1},\"doorEventType\":\"RoomOpened\"}";
        EventRoomDoor doorEvent = new EventRoomDoor();
        when(objectMapper.readValue(message, Event.class)).thenReturn(doorEvent);
        when(roomRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // Act
        eventServiceImplementation.processEvent(message);

        // Assert
        // Verify that the eventRepository's save method was not called
        verify(eventRepository, never()).save(any());
    }
    @Test
    void getAllEventsDtoByRoomId() {

        Long roomId = 1L;
        Room room = new Room();
        room.setId(roomId);

        EventRoomCleaning cleaningEvent = mock(EventRoomCleaning.class);
        when(cleaningEvent.getRoom()).thenReturn(room);

        EventRoomDoor doorEvent = mock(EventRoomDoor.class);
        when(doorEvent.getRoom()).thenReturn(room);

        List<Event> events = new ArrayList<>();
        events.add(cleaningEvent);
        events.add(doorEvent);

        when(eventRepository.findAllByRoomId(roomId)).thenReturn(events);

        List<EventDto> eventDtos = eventServiceImplementation.getAllEventsDtoByRoomId(roomId);

        assertEquals(events.size(), eventDtos.size());

        verify(eventRepository, times(1)).findAllByRoomId(roomId);
    }


    @Test
    public void deserializeEventTest() throws Exception {

        String validJson = "{ \"doorEventType\": \"OPEN\", \"timestamp\": \"2024-05-22T14:30:00\", \"room\": { \"roomNo\": \"101\" } }";

        Room room = new Room();
        room.setId(101L);

        EventRoomDoor expectedEvent = new EventRoomDoor(LocalDateTime.of(2024, 5, 22, 14, 30), room, null, "OPEN");

        when(objectMapper.readValue(validJson, Event.class)).thenReturn(expectedEvent);

        Event actualEvent = eventServiceImplementation.deserializeEvent(validJson);

        assertEquals(expectedEvent, actualEvent);
    }
}
