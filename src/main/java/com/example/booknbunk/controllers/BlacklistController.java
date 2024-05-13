package com.example.booknbunk.controllers;

import com.example.booknbunk.services.implementations.BlacklistServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blacklist")
public class BlacklistController {

    private final BlacklistServiceImplementation blacklistService;

    @Autowired
    public BlacklistController(BlacklistServiceImplementation blacklistService) {
        this.blacklistService = blacklistService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToBlacklist(@RequestBody String email) {
        blacklistService.addToBlacklist(email);
        return new ResponseEntity<>("Email added to blacklist", HttpStatus.OK);
    }

   // @PutMapping("/update")
   // public ResponseEntity<String> updateBlackList()

}

// /api/blacklist/add/test@gmail.com