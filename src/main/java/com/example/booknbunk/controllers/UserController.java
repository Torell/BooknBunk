package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.Role;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.RoleRepository;
import com.example.booknbunk.repositories.UserRepository;
import com.example.booknbunk.services.implementations.UserDetailsServiceImplementation;
import com.example.booknbunk.services.interfaces.MyUserService;
import com.example.booknbunk.utils.RolesForm;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('Admin')")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MyUserService userService;


    public UserController(UserRepository userRepository, RoleRepository roleRepository, MyUserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addBooking(User user, @RequestParam(value = "rolesSelected",required = false) List<UUID> rolesSelected){

        userService.addUser(user,rolesSelected);
        
        return "redirect:/user/createUser";
    }

    @RequestMapping("/createUser")
    public String createBooking(Model model) {
        User user = new User();
        user.setRoles(new ArrayList<>());

        model.addAttribute("user", user);
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("rolesForm",new RolesForm());
        return "user/addUser";

    }
}
