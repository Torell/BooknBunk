package com.example.booknbunk.repositories;

import com.example.booknbunk.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Ändra kunds uppgifter

}
