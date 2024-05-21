package com.example.booknbunk.services.implementations;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlacklistServiceImplementationTestIntegration {

    private static final String BLACKLIST_API_TEST_LOCAL = "blacklist.json";


    @Test
    void readProperties() throws IOException {

        try (InputStream jsonFile = getClass().getClassLoader().getResourceAsStream("blacklist.json")) {
            assert jsonFile != null;
            Scanner scanner = new Scanner(jsonFile).useDelimiter("\\A");
            String result = scanner.hasNext() ? scanner.next() : "";

            assertTrue(result.contains("\"id\""));
            assertTrue(result.contains("\"email\""));
            assertTrue(result.contains("\"name\""));
            assertTrue(result.contains("\"group\""));
            assertTrue(result.contains("\"created\""));
            assertTrue(result.contains("\"ok\""));


        }

    }

}