package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.EventDto;
import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.EventRoomClosed;
import com.example.booknbunk.models.EventRoomOpened;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    void processEventSuccesfullTest() throws JsonProcessingException {

        String mess = "{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-28T11:01:07.237941136\",\"RoomNo\":\"1\"}";
        EventRoomClosed doorEvent = new EventRoomClosed();

        doorEvent.setId(1L);
        doorEvent.setRoomNo(1L);

        Room room = new Room();
        room.setId(1L);

        when(objectMapper.readValue(mess, Event.class)).thenReturn(doorEvent);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        eventServiceImplementation.processEvent(mess);

        verify(eventRepository, times(1)).save(doorEvent);
        verify(roomRepository, times(1)).findById(1L);
        verify(eventRepository).save(doorEvent);
        verifyNoMoreInteractions(eventRepository, roomRepository);
    }

   @Test
    void processEventErrorTest() throws JsonProcessingException {

        String message = "Invalid JSON message";
        when(objectMapper.readValue(message, Event.class)).thenThrow(new RuntimeException("Process event error"));

        eventServiceImplementation.processEvent(message);

        verify(eventRepository, never()).save(any());
    }

    @Test
    void processEventRoomNotFoundErrorTest() throws JsonProcessingException {

        String message = "{\"type\":\"RoomClosed\",\"TimeStamp\":\"2024-05-28T11:01:07.237941136\",\"RoomNo\":\"1\"}";
        EventRoomOpened doorEvent = new EventRoomOpened();
        when(objectMapper.readValue(message, Event.class)).thenReturn(doorEvent);
        when(roomRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        eventServiceImplementation.processEvent(message);

        verify(eventRepository, never()).save(any());
    }
    /*
    @Test
    void getAllEventsDtoByRoomIdTest() {

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
*/
}
