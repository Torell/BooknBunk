package com.example.booknbunk;

import com.example.booknbunk.configurations.IntegrationProperties;
import com.example.booknbunk.services.interfaces.EventService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;


@Component
@ComponentScan
public class FetchAllEvents implements CommandLineRunner {

    private final EventService eventService;

    IntegrationProperties integrationProperties;

    public FetchAllEvents(EventService eventService, IntegrationProperties integrationProperties) {
        this.eventService = eventService;
        this.integrationProperties = integrationProperties;
    }

    @Override
    public void run(String... args) throws Exception {
       ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(integrationProperties.getEvent().getHost());
        factory.setUsername(integrationProperties.getEvent().getUsername());
        factory.setPassword(integrationProperties.getEvent().getPassword());
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            eventService.processEvent(message);
        };
        channel.basicConsume(integrationProperties.getEvent().getQueueName(), true, deliverCallback, consumerTag -> { });
    }

}



