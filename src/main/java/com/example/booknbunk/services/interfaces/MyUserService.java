package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MyUserService {
    void addUser(User user, List<UUID> roles);


    Page<User> findAllUserPagesWithSearch(String search, Pageable pageable);

    User findUserById(UUID id);

    void updateUser(User user, List<UUID> roles);
}
