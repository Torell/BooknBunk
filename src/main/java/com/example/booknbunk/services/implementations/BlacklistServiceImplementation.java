package com.example.booknbunk.services.implementations;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.example.booknbunk.services.interfaces.BlacklistService;
import com.example.booknbunk.utils.Blacklist;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

@Service
public class BlacklistServiceImplementation implements BlacklistService {

    private static final String BLACKLIST_API_URL = "https://javabl.systementor.se/api/booknbunk/blacklist";

    private  ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addToBlacklist(Blacklist blacklist) throws JsonProcessingException {

        if (!checkBlacklist(blacklist.getEmail())) {

            String requestBody = objectMapper.writeValueAsString(blacklist);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BLACKLIST_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void removeFromBlacklist(Blacklist blacklist) throws JsonProcessingException {


            String requestBody = objectMapper.writeValueAsString(blacklist);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BLACKLIST_API_URL + "/" + blacklist.getEmail()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }




    @Override
    public boolean checkBlacklist(String email){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BLACKLIST_API_URL + "/checkblacklist/" + email))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonMapper mapper = new JsonMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Blacklist blacklist = mapper.readValue(response.body(), Blacklist.class);
            return blacklist.isOk();

        } catch (IOException e) {
            System.out.println("Not found on the Blacklist");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
