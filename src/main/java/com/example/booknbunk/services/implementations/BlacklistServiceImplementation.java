package com.example.booknbunk.services.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.example.booknbunk.services.interfaces.BlacklistService;
import com.example.booknbunk.utils.Blacklist;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

@Service
public class BlacklistServiceImplementation implements BlacklistService {

    private static final String BLACKLIST_API_URL = "https://javabl.systementor.se/api/booknbunk/blacklist";

    @Override
    public void addToBlacklist(String email) {
        try {
            URL url = new URL(BLACKLIST_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Skicka e-posten som JSON
            String jsonInputString = "{\"email\": \"" + email + "\"}";
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Läs svarskoden (om du behöver)
            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

            // Stäng anslutningen
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            // Hantera eventuella fel här
        }
    }


    @Override
    public boolean checkBlacklist(String email){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://javabl.systementor.se/api/stefan/blacklistcheck/" + email))
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
