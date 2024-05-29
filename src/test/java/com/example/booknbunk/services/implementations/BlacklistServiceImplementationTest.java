package com.example.booknbunk.services.implementations;

import com.example.booknbunk.configurations.BlacklistProperties;
import com.example.booknbunk.configurations.IntegrationProperties;
import com.example.booknbunk.utils.Blacklist;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class BlacklistServiceImplementationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private HttpClient httpClient;
    private HttpResponse<String> httpResponse;
    private BlacklistServiceImplementation blacklistService;

    private IntegrationProperties integrationProperties;

    private BlacklistProperties blacklistProperties;

    @BeforeEach
    void setUp() {
        httpClient = Mockito.mock(HttpClient.class);
        httpResponse = Mockito.mock(HttpResponse.class);
        integrationProperties = Mockito.mock(IntegrationProperties.class);
        BlacklistProperties blacklistProperties = Mockito.mock(BlacklistProperties.class);

        when(integrationProperties.getBlacklist()).thenReturn(blacklistProperties);
        when(blacklistProperties.getUrl()).thenReturn("https://example.com/blacklist");

        blacklistService = new BlacklistServiceImplementation(integrationProperties, httpClient, new ObjectMapper());
    }

    @Test
    void addToBlacklist() throws IOException, InterruptedException {
        Blacklist blacklist = new Blacklist("test", "email@test.com", false);

        String checkResponse = "{\"ok\":false}";
        String addResponse = "{\"ok\":true}";

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(checkResponse).thenReturn(addResponse);

        blacklistService.addToBlacklist(blacklist);

        // Two invocations because the method first checks if the email already is on the blacklist
        verify(httpClient, times(2)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }


    @Test
    void removeFromBlacklist() throws IOException, InterruptedException {
        Blacklist blacklist = new Blacklist("test", "email@test.com", true);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        blacklistService.removeFromBlacklist(blacklist);

        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void checkBlacklist() throws IOException, InterruptedException {
        String email = "email@test.com";
        String jsonResponse = "{\"name\":\"name\",\"email\":\"email@test.com\",\"ok\":false}";

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(jsonResponse);

        boolean isBlacklisted = blacklistService.checkBlacklist(email);

        // confusing but correct, if the email is blacklisted, checkBlacklist should return false.
        // the json file should instead have the property "Blacklisted" to make this less confusing
        assertFalse(isBlacklisted);
        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
    @Test
    void CheckBlacklistNotFound() throws IOException, InterruptedException {
        String email = "nonexistent@test.com";

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Not found"));

        boolean isBlacklisted = blacklistService.checkBlacklist(email);

        assertFalse(isBlacklisted);
        verify(httpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }
}