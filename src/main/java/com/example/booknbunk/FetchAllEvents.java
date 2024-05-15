package com.example.booknbunk;

import com.example.booknbunk.models.Event;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import com.fasterxml.jackson.databind.ObjectMapper;


@ComponentScan
public class FetchAllEvents implements CommandLineRunner {
    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;

    public FetchAllEvents(EventRepository eventRepository, RoomRepository roomRepository) {
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
    }

    private String queueName = "61da1eea-c67b-4707-9d2d-4ce48ecaf461";

    @Override
    public void run(String... args) throws Exception {
       ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
           // Event event = mapper.readValue(message, Event.class);

           // insertEventIntoDatabase(event);
            try {
                // Deserialisera JSON-meddelandet till en Event-instans
                Event event = mapper.readValue(message, Event.class);

                // Spara eventet i databasen
                eventRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
/*
    private static void insertEventIntoDatabase(Event event) {
        try {
            Connection dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/yourdb", "youruser", "yourpassword");
            String insertEvent = "INSERT INTO Events (room_id, event_type, event_timestamp) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = dbConnection.prepareStatement(insertEvent)) {
                stmt.setLong(1, event.getRoom().getId());
                stmt.setString(2, event.getClass().getSimpleName());
                stmt.setTimestamp(3, Timestamp.valueOf(event.getTimeStamp()));
                stmt.executeUpdate();
            }
            dbConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}



