package com.example.booknbunk.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Pattern(regexp = "^[^\\d]*$", message = "Name cannot contain numbers")
    private String name;
    @NotNull
    @Email(message = "Should be a valid email adress")
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;


    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Customer(Long id, String name, String email, List<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bookings = bookings;
    }


}
