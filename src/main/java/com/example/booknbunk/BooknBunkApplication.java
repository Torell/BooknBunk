package com.example.booknbunk;

import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooknBunkApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooknBunkApplication.class, args);
    }
/*
    @Autowired
    private RoomRepository roomRepository;

    @Bean
    public CommandLineRunner bootstrapRooms(){
        return args -> {
            Room room1 = new Room(0);
            Room room2 = new Room(1);
            Room room3 = new Room(2);

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
        };
    } */


}
