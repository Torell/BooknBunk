package com.example.booknbunk.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCustomerDetailedDTO {

    @Id
    @GeneratedValue

    private int id;
    private int externalSystemId;
    private String companyName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    private int postalCode;
    private String country;
    private String contactName;
    private String phone;
    private String fax;
}
