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

    private Long localId;
    private Long externalSystemId;
    private String companyName;
    private String contactTitle;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String country;
    private String contactName;
    private String phone;
    private String fax;
}
