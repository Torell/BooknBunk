package com.example.booknbunk;

import com.example.booknbunk.configurations.IntegrationProperties;
import com.example.booknbunk.models.Event;
import com.example.booknbunk.repositories.EventRepository;
import com.example.booknbunk.repositories.RoomRepository;
import com.example.booknbunk.services.interfaces.EventService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;


@Component
//@Component
@ComponentScan
public class FetchAllEvents implements CommandLineRunner {

    private final EventService eventService;

    public FetchAllEvents(EventService eventService) {
        this.eventService = eventService;
    }


    @Autowired
    IntegrationProperties integrationProperties;



    @Override
    public void run(String... args) throws Exception {
       ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(integrationProperties.getEvent().getHost());
        factory.setUsername(integrationProperties.getEvent().getUsername());
        factory.setPassword(integrationProperties.getEvent().getPassword());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            eventService.processEvent(message);
        };
        channel.basicConsume(integrationProperties.getEvent().getQueueName(), true, deliverCallback, consumerTag -> { });
    }

}



