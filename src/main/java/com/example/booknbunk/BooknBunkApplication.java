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

        @Bean
        public CommandLineRunner commandLineRunner(CustomerRepository customerRepo, RoomRepository roomRepo){
            return args -> {

                Customer c1 = new Customer("Alma", "alma@gmail.com");
                Customer c2 = new Customer("Berra",  "berra@gmail.com");
                Customer c3 = new Customer("Cissi",  "cissi@gmail.com");
                Customer c4 = new Customer("David", "david@gmail.com");
                Customer c5 = new Customer("Elmer",  "elmer@gmail.com");

                customerRepo.save(c1);
                customerRepo.save(c2);
                customerRepo.save(c3);
                customerRepo.save(c4);
                customerRepo.save(c5);

                Room room1 = new Room(0);
                Room room2 = new Room(1);
                Room room3 = new Room(2);

                roomRepository.save(room1);
                roomRepository.save(room2);
                roomRepository.save(room3);

            };

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
