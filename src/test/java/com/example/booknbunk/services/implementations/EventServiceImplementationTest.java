package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.Event;
import com.example.booknbunk.models.EventRoomDoor;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EventServiceImplementationTest {

    @Mock
    private EventRepository mockEventRepository;

    @Mock
    private RoomRepository mockRoomRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EventServiceImplementation eventService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        eventService = new EventServiceImplementation(mockEventRepository, mockRoomRepository, objectMapper);
    }

    @Test
    public void deserializeEventTest(){
        String validJson = "{\"type\":\"RoomOpened\",\"TimeStamp\":\"2024-05-16T09:40:13.034814117\",\"RoomNo\":\"5\"}";

        Event event = eventService.deserializeEvent(validJson);

        //att det inte är null
        assertNotNull(event);
        //att det är ett door-event
        assertTrue(event instanceof EventRoomDoor);
        //att eventype är korrekt
        String expectedDoorEventType = "RoomOpened";
        EventRoomDoor doorEvent = (EventRoomDoor) event;
        String actualDoorEventType = doorEvent.getDoorEventType();
        assertTrue(expectedDoorEventType.equalsIgnoreCase(actualDoorEventType));

        //att timestamp är rätt
        LocalDateTime expectedTimeStamp = LocalDateTime.parse("2024-05-16T09:40:13.034814117");
        LocalDateTime actualTimeStamp =  event.getTimeStamp();
        assertEquals(expectedTimeStamp, actualTimeStamp);

        //att room no är rätt
        Long expectedRoomNo = 5L;
        Long actualRoomNo = event.getRoomNo();
        assertTrue(expectedRoomNo == actualRoomNo);




    }


    //asserta hur många ggr



}
