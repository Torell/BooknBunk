package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.BookingDetailedDto;
import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.dtos.RoomDetailedDto;
import com.example.booknbunk.dtos.RoomMiniDto;
import com.example.booknbunk.models.Role;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.RoleRepository;
import com.example.booknbunk.repositories.UserRepository;
import com.example.booknbunk.services.implementations.UserDetailsServiceImplementation;
import com.example.booknbunk.services.interfaces.MyUserService;
import com.example.booknbunk.utils.RolesForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String addUser(User user, @RequestParam(value = "rolesSelected",required = false) List<UUID> rolesSelected){

        userService.addUser(user,rolesSelected);
        
        return "redirect:user/createUser";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {

        userRepository.deleteById(id);

        return "redirect:user/getAll";
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable("id")UUID id, Model model) {
        User user = userService.findUserById(id);

        model.addAttribute("user",user);
        model.addAttribute("roles",roleRepository.findAll());

        return "user/editUser";

    }

    @RequestMapping("/updateUser")
    public String updateUser(User user,@RequestParam(value = "rolesSelected",required = false) List<UUID> rolesSelected) {

        userService.updateUser(user,rolesSelected);

        return "redirect:/user/getAll";
    }



    @RequestMapping("/createUser")
    public String createUser(Model model) {
        User user = new User();
        user.setRoles(new ArrayList<>());


        model.addAttribute("user", user);
        model.addAttribute("roles",roleRepository.findAll());
        model.addAttribute("rolesForm",new RolesForm());
        return "user/addUser";

    }

    @GetMapping("/getAll")
    public String getAllContractCustomersWithSearchAndOrder(Model model,
                                                            @RequestParam(defaultValue = "0")int page,
                                                            @RequestParam(defaultValue = "username")String sort,
                                                            @RequestParam(defaultValue = "asc")String sortDirection,
                                                            @RequestParam(required = false, defaultValue = "")String search)
    {
        int pageSize = 30;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.fromString(sortDirection),sort));
        Page<User> userPage = userService.findAllUserPagesWithSearch(search,pageable);
        model.addAttribute("userPage",userPage);
        model.addAttribute("search",search);
        model.addAttribute("sort",sort);
        model.addAttribute("sortDirection",sortDirection);
        return "user/allUsers";

    }
}
