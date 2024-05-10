package com.example.booknbunk;

import com.example.booknbunk.models.Booking;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.models.Room;
import com.example.booknbunk.repositories.BookingRepository;
import com.example.booknbunk.repositories.CustomerRepository;
import com.example.booknbunk.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@SpringBootApplication
public class BooknBunkApplication {

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(BooknBunkApplication.class, args);
        } else if (Objects.equals(args[0], "fetchallcustomers")) {
            SpringApplication springApplication = new SpringApplication(FetchAllContractCustomers.class);
            springApplication.setWebApplicationType(WebApplicationType.NONE);
            springApplication.run(args);
        } else if (Objects.equals(args[0], "shippers")) {
            SpringApplication springApplication = new SpringApplication(FetchAllShippingCompanys.class);
            springApplication.setWebApplicationType(WebApplicationType.NONE);
            springApplication.run(args);
        }
    }


    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository, RoomRepository roomRepository, BookingRepository bookingRepository) {
        return args -> {

            Customer c1 = new Customer("Alma", "alma@gmail.com");
            Customer c2 = new Customer("Berra", "berra@gmail.com");
            Customer c3 = new Customer("Cissi", "cissi@gmail.com");
            Customer c4 = new Customer("David", "david@gmail.com");
            Customer c5 = new Customer("Elmer", "elmer@gmail.com");

            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
            customerRepository.save(c4);
            customerRepository.save(c5);

            Room room1 = new Room(0);
            Room room2 = new Room(1);
            Room room3 = new Room(2);

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);

            Booking booking1 = new Booking(LocalDate.of(2024, 5, 7), LocalDate.of(2024, 5, 8),
                    room1, 0, c2);

            Booking booking2 = new Booking(LocalDate.of(2024, 5, 7), LocalDate.of(2024, 5, 10),
                    room2, 1, c3);

            Booking booking3 = new Booking(LocalDate.of(2024, 5, 11), LocalDate.of(2024, 5, 14),
                    room3, 2, c4);

            Booking booking4 = new Booking(LocalDate.of(2024, 5, 15), LocalDate.of(2024, 5, 17),
                    room1, 0, c5);

            Booking booking5 = new Booking(LocalDate.of(2024, 5, 18), LocalDate.of(2024, 5, 20),
                    room2, 1, c1);

            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingRepository.save(booking3);
            bookingRepository.save(booking4);
            bookingRepository.save(booking5);
        };

    }
}






