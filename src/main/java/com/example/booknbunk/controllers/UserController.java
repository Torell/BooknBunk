package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.RoleRepository;
import com.example.booknbunk.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('Admin')")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/add")
    public String addBooking(Model model, RedirectAttributes redirectAttributes, User user){

        userRepository.save(user);
        return "redirect:/user/createUser";
    }

    @RequestMapping("/createUser")
    public String createBooking(Model model) {
        User user = new User();

        model.addAttribute("user", user);
        return "/user/addUser";

    }
}
