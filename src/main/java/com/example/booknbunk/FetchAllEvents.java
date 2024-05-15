package com.example.booknbunk;

import com.example.booknbunk.repositories.EventRepository;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.amqp.ConnectionFactoryCustomizer;
import org.springframework.context.annotation.ComponentScan;
import com.fasterxml.jackson.databind.ObjectMapper;


@ComponentScan
public class FetchAllEvents implements CommandLineRunner {
    private final EventRepository eventRepository;

    public FetchAllEvents(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
// https://www.baeldung.com/jackson-annotations#bd-jackson-polymorphic-type-handling-annotations

        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

    }

