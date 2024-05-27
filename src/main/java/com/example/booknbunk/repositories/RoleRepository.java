package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {

    Role findByName(String name);

}
