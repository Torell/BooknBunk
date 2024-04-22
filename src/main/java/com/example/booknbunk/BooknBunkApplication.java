package com.example.booknbunk;

import com.example.booknbunk.models.Customer;
import com.example.booknbunk.repositories.CustomerRepository;
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
        public CommandLineRunner commandLineRunner(CustomerRepository customerRepo){
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

            };

        }




}
