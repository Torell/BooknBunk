package com.example.booknbunk.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
public class Shipper {

    @Id
    @GeneratedValue
    private Long id;
    private String companyName;
    private String phoneNumber;

    public Shipper(Long id, String companyName, String phoneNumber) {
        this.id = id;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }
}
