package com.example.booknbunk;

import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ShippingIntegration {

    private static final String SHIPPERS_API_TEST_LOCAL = "shippers.json";
    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testFetchAndSaveShippers() throws Exception {
        // Ladda JSON-filen
        InputStream jsonFile = getClass().getClassLoader().getResourceAsStream("shippers.json");
        assert jsonFile != null;

        // Deserialisera JSON-filen till Shipper array
        Shipper[] shippers = objectMapper.readValue(jsonFile, Shipper[].class);

        // Spara varje Shipper objekt i databasen
        for (Shipper shipper : shippers) {
            shipperRepository.save(shipper);
        }

        // Verifiera att objekten har sparats i databasen
        List<Shipper> savedShippers = shipperRepository.findAll();
        assertEquals(shippers.length, savedShippers.size());

        // Extra verifieringar kan läggas till här
        for (Shipper shipper : shippers) {
            assertTrue(savedShippers.stream()
                    .anyMatch(savedShipper -> savedShipper.getCompanyName().equals(shipper.getCompanyName())
                            && savedShipper.getPhone().equals(shipper.getPhone())));
        }
    }
    @Test
    void readShippingProperties() throws IOException {

        try (InputStream jsonFile = getClass().getClassLoader().getResourceAsStream("shippers.json")) {
            assert jsonFile != null;
            Scanner scanner = new Scanner(jsonFile).useDelimiter("\\A");
            String result = scanner.hasNext() ? scanner.next() : "";

            assertTrue(result.contains("\"id\""));
            assertTrue(result.contains("\"companyName\""));
            assertTrue(result.contains("\"phone\""));
        }
    }


}
