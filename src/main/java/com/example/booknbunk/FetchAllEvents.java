package com.example.booknbunk;

import com.example.booknbunk.repositories.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.amqp.ConnectionFactoryCustomizer;
import org.springframework.context.annotation.ComponentScan;


//import com.rabbitmq.client.ConnectionFactory;

@ComponentScan
public class FetchAllEvents implements CommandLineRunner {
    private final EventRepository eventRepository;

    public FetchAllEvents(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    private String queueName = "61da1eea-c67b-4707-9d2d-4ce48ecaf461";

    @Override
    public void run(String... args) throws Exception {
       // ConnectionFactory factory = new ConnectionFactory();

    }
}
