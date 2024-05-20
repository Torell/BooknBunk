package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.example.booknbunk.services.interfaces.ShippingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class ShippingServiceImplementation implements ShippingService{

    private final ShipperRepository shipperRepository;
    private final ObjectMapper objectMapper;

    public ShippingServiceImplementation(ShipperRepository shipperRepository, ObjectMapper objectMapper) {
        this.shipperRepository = shipperRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void fetchAndSaveShippers() {
        try {
            URL url = new URL("https://javaintegration.systementor.se/shippers");
            Shipper[] shippers = objectMapper.readValue(url, Shipper[].class);

            for (Shipper shipper : shippers) {
                shipperRepository.save(shipper);
            }
        } catch (Exception e) {
            // Hantera fel här, t.ex. logga eller kasta ett undantag
            e.printStackTrace();
        }
    }
}
