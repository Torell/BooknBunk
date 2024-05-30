package com.example.booknbunk.services.implementations;

import com.example.booknbunk.configurations.IntegrationProperties;
import com.example.booknbunk.services.interfaces.BlacklistService;
import com.example.booknbunk.utils.Blacklist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BlacklistServiceImplementation implements BlacklistService {



    @Autowired
    IntegrationProperties integrationProperties;

    //private static final String BLACKLIST_API_URL = "https://javabl.systementor.se/api/booknbunk/blacklist";
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public BlacklistServiceImplementation() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();

    }

    public BlacklistServiceImplementation(IntegrationProperties integrationProperties, HttpClient httpClient, ObjectMapper objectMapper) {
        this.integrationProperties = integrationProperties;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    // Constructor for testing
    public BlacklistServiceImplementation(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    // Setter for testing
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void addToBlacklist(Blacklist blacklist) throws JsonProcessingException {
       // if (!checkBlacklist(blacklist.getEmail())) {
            String requestBody = objectMapper.writeValueAsString(blacklist);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(integrationProperties.getBlacklist().getUrl()))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            try {
                System.out.println("url: " + request.uri());
                System.out.println(requestBody);
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
 //   }

    @Override
    public void removeFromBlacklist(Blacklist blacklist) throws JsonProcessingException {
        String requestBody = objectMapper.writeValueAsString(blacklist);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(integrationProperties.getBlacklist().getUrl() + "/" + blacklist.getEmail()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkBlacklist(String email) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(integrationProperties.getBlacklist().getUrl() + "check/" + email))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonMapper mapper = new JsonMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Blacklist blacklist = mapper.readValue(response.body(), Blacklist.class);
            System.out.println("blacklist: " + blacklist.isOk());
            return blacklist.isOk();
        } catch (IOException e) {
            System.out.println("Not found on the Blacklist");
            return false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
