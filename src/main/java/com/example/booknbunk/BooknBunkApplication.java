package com.example.booknbunk;

import com.example.booknbunk.security.UserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.booknbunk.configurations")
public class BooknBunkApplication {

    @Autowired
    private UserDataSeeder userDataSeeder;

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(BooknBunkApplication.class, args);
        } else if (Objects.equals(args[0], "fetch")) {
            SpringApplication springApplication = new SpringApplication(FetchAllContractCustomers.class);
            springApplication.setWebApplicationType(WebApplicationType.NONE);
            springApplication.run(args);
        } else if (Objects.equals(args[0], "shippers")) {
            SpringApplication springApplication = new SpringApplication(FetchAllShippingCompanys.class);
            springApplication.setWebApplicationType(WebApplicationType.NONE);
            springApplication.run(args);
        } else if (Objects.equals(args[0], "events")) {
            SpringApplication springApplication = new SpringApplication(FetchAllEvents.class);
            springApplication.setWebApplicationType(WebApplicationType.NONE);
            springApplication.run(args);
        }
    }


    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userDataSeeder.Seed();
        };
    }
}
