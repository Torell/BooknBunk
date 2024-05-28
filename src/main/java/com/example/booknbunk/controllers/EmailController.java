package com.example.booknbunk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class EmailController {

    private static final String TEMPLATE_PATH = "src/main/resources/templates/emailTemplate.html";

    @GetMapping("/edit-template")
    public String editTemplate(Model model) throws IOException {
        Path path = Paths.get(TEMPLATE_PATH);
        String content = Files.readString(path);
        model.addAttribute("content", content);
        return "editTemplate";
    }

    @PostMapping("/save-template")
    public String saveTemplate(@RequestParam("content") String content) throws IOException {
        Path path = Paths.get(TEMPLATE_PATH);
        Files.writeString(path, content);
        return "redirect:/edit-template?success";
    }
}

