package com.example.booknbunk;

import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FetchAllShippingCompanysTest {

    @Mock
    private ShipperRepository shipperRepositoryMock;

    @Spy
    private ObjectMapper objectMapperSpy;

    private FetchAllShippingCompanys fetchAllShippingCompanys;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        fetchAllShippingCompanys = new FetchAllShippingCompanys(shipperRepositoryMock);
    }

   /* @Test
    public void testRun() throws Exception {
        // Given
        URL url = new URL("https://javaintegration.systementor.se/shippers");
        Shipper[] shippers = {new Shipper(1L, "Company 1", "123456789"),
                new Shipper(2L, "Company 2", "987654321")};
        when(objectMapperSpy.readValue(url, Shipper[].class)).thenReturn(shippers);

        // When
        fetchAllShippingCompanys.setObjectMapper(objectMapperSpy);
        fetchAllShippingCompanys.run();

        // Then
        verify(shipperRepositoryMock, times(2)).save(any(Shipper.class));
        verify(objectMapperSpy, times(2)).readValue(url, Shipper[].class);
    }*/
}

