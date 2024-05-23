package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.models.User;

import java.util.List;
import java.util.UUID;

public interface MyUserService {
    void addUser(User user, List<UUID> roles);
}
