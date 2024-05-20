package com.example.booknbunk;


import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.example.booknbunk.services.implementations.ShippingServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


import java.net.URL;

@ComponentScan
public class FetchAllShippingCompanys implements CommandLineRunner {
    private final ShippingServiceImplementation shippingService;

    public FetchAllShippingCompanys(ShippingServiceImplementation shippingService) {
        this.shippingService = shippingService;
    }

    @Override
    public void run(String... args) throws Exception {
        shippingService.fetchAndSaveShippers();
    }
}
  /*  private final ShipperRepository shipperRepository;

    public FetchAllShippingCompanys(ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        fetchAndSaveShippers();
    }

    public void fetchAndSaveShippers() throws Exception {
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        ObjectMapper objectMapper = new ObjectMapper();
        Shipper[] shippers = objectMapper.readValue(url, Shipper[].class);

        for (Shipper shipper : shippers) {
            shipperRepository.save(shipper);
        }
    }
}

/*@ComponentScan
public class FetchAllShippingCompanys implements CommandLineRunner {
    private final ShipperRepository shipperRepository;


    public FetchAllShippingCompanys(ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        URL url = new URL("https://javaintegration.systementor.se/shippers");
        ObjectMapper objectMapper = new ObjectMapper();
        Shipper[] shippers = objectMapper.readValue(url, Shipper[].class);

        for (Shipper shipper : shippers) {
            shipperRepository.save(shipper);
        }
    }
}*/
