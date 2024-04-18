package com.example.booknbunk.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    //Todo: kardinalitet lägg till
    String name;
    String email;

    public Customer(String name, String email){
        this.name = name;
        this.email = email;
    }


}
