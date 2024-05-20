package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URL;

import static org.mockito.Mockito.*;

class ShippingServiceImplementationTest {

    @Mock
    private ShipperRepository shipperRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ShippingServiceImplementation shippingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchAndSaveShippers() throws Exception {
        // Skapar en array av Shipper-objekt som ska returneras av mock-objektet
        Shipper[] shippers = new Shipper[]{new Shipper(1L, "Shipping Company Test", "123456789")};

        // Mockar objectMapper.readValue för att returnera shippers när den anropas med valfri URL och Shipper[].class
        when(objectMapper.readValue(any(URL.class), eq(Shipper[].class))).thenReturn(shippers);

        // Anropar metoden vi testar
        shippingService.fetchAndSaveShippers();

        // Verifierar att objectMapper.readValue anropades exakt en gång med valfri URL och Shipper[].class
        verify(objectMapper, times(1)).readValue(any(URL.class), eq(Shipper[].class));

        // Verifierar att shipperRepository.save anropades en gång för varje Shipper-objekt i arrayen
        verify(shipperRepository, times(shippers.length)).save(any(Shipper.class));
    }
}