package com.example.booknbunk.controllers;

import com.example.booknbunk.services.implementations.BlacklistServiceImplementation;
import com.example.booknbunk.utils.Blacklist;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blacklist")

public class BlacklistController {

    private final BlacklistServiceImplementation blacklistService;

    @Autowired
    public BlacklistController(BlacklistServiceImplementation blacklistService) {
        this.blacklistService = blacklistService;
    }

    @PostMapping("/add")
    public String add(Blacklist blacklist) throws JsonProcessingException {

        blacklist.setOk(false);
        blacklistService.addToBlacklist(blacklist);

        return "redirect:/blacklist/addToBlacklist";

    }

    @RequestMapping("/addToBlacklist")
    public String addToBlacklist(Model model) {

        Blacklist blacklist = new Blacklist();
        model.addAttribute("blacklist",blacklist);

        return "blacklist/addToBlacklist";

    }

    @GetMapping("/remove")
    public String remove(Blacklist blacklist) throws JsonProcessingException {
        System.out.println("here 2");
        blacklist.setOk(true);
        blacklistService.removeFromBlacklist(blacklist);

        return "redirect:/blacklist/removeFromBlacklist";

    }

    @RequestMapping("/removeFromBlacklist")

    public String removeFromBlacklist(Model model) {

        System.out.println("here 1");

        Blacklist blacklist = new Blacklist();
        model.addAttribute("blacklist",blacklist);

        return "blacklist/editBlacklist";

    }
}