package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@AllArgsConstructor
public class RoomController {

   private final RoomRepository roomRepository;
   private final RoomService roomService;


   @RequestMapping("/{id}")
   public RoomDetailedDto findRoomById(@PathVariable long id) {
      return roomService.findRoomById(id);
   }






}
