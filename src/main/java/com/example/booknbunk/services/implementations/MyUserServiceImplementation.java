package com.example.booknbunk.services.implementations;

import com.example.booknbunk.models.Role;
import com.example.booknbunk.models.User;
import com.example.booknbunk.repositories.RoleRepository;
import com.example.booknbunk.repositories.UserRepository;
import com.example.booknbunk.services.interfaces.MyUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class MyUserServiceImplementation implements MyUserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public MyUserServiceImplementation(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user, List<UUID> roles) {

        Collection<Role> rolesToAdd = new ArrayList<>();
        roleRepository.findAllById(roles).forEach(rolesToAdd::add);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(user.getPassword());

        user.setRoles(rolesToAdd);
        user.setPassword(hash);

        userRepository.save(user);
    }
}
