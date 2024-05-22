package com.example.booknbunk;

import com.example.booknbunk.services.interfaces.EventService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
public class FetchAllEvents implements CommandLineRunner {

    private final EventService eventService;

    public FetchAllEvents(EventService eventService) {
        this.eventService = eventService;
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


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            eventService.processEvent(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }

}



