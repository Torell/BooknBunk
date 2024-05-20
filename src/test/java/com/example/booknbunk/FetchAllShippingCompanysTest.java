package com.example.booknbunk;

import com.example.booknbunk.models.Shipper;
import com.example.booknbunk.repositories.ShipperRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FetchAllShippingCompanysTest {

    @Mock
    private ShipperRepository shipperRepository;

    private FetchAllShippingCompanys fetchAllShippingCompanys;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fetchAllShippingCompanys = new FetchAllShippingCompanys(shipperRepository);
    }

    @Test
    public void testRun() throws Exception {
        // Given
        ObjectMapper objectMapper = new ObjectMapper();
        URL mockedUrl = mock(URL.class);
        Shipper[] shippers = objectMapper.readValue(
                "[{\"id\":1,\"companyName\":\"Svensson-Karlsson\",\"phone\":\"0705693764\"}]", Shipper[].class);

        when(mockedUrl.openConnection()).thenReturn(null); // Ska det vara null?
        when(objectMapper.readValue(any(URL.class), eq(Shipper[].class))).thenReturn(shippers);

        // When
        fetchAllShippingCompanys.run();

        // Then
        verify(shipperRepository, times(1)).save(any(Shipper.class));
    }
}
