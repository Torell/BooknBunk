package com.example.booknbunk.controllers;

import com.example.booknbunk.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoomController {

   private final RoomRepository roomRepository;





}
