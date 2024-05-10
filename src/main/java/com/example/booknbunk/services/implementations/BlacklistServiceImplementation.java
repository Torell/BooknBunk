package com.example.booknbunk.services.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class BlacklistServiceImplementation {

    private static final String BLACKLIST_API_URL = "https://javabl.systementor.se/api/booknbunk/blacklist";

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
}
