package com.example.booknbunk;


import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.net.URL;

@Component
public class FetchAllShippingCompanys implements CommandLineRunner {
    private final ShipperRepository shipperRepository;
    @Autowired
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
}
