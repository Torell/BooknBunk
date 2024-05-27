package com.example.booknbunk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "/forgotPassword";
    }
}
