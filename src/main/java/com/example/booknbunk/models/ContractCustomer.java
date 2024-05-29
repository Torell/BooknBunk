package com.example.booknbunk.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "customers")
public class ContractCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long localId;

    @JacksonXmlProperty(localName = "id")
    @NotNull(message = "External system ID cannot be null")
    private Long externalSystemId;

    @NotBlank(message = "Company name is mandatory")
    @Size(max = 255, message = "Company name must be less than 255 characters")
    private String companyName;

    @NotBlank(message = "Contact title is mandatory")
    @Size(max = 100, message = "Contact title must be less than 100 characters")
    private String contactTitle;

    @NotBlank(message = "Street address is mandatory")
    @Size(max = 255, message = "Street address must be less than 255 characters")
    private String streetAddress;

    @NotBlank(message = "City is mandatory")
    @Size(max = 100, message = "City must be less than 100 characters")
    private String city;

    @NotNull(message = "Postal code is mandatory")
    @Pattern(regexp = "\\d{4,5}", message = "Postal code must be 4 or 5 digits")
    private String postalCode;


    @NotBlank(message = "Country is mandatory")
    @Size(max = 100, message = "Country must be less than 100 characters")
    private String country;

    @NotBlank(message = "Contact name is mandatory")
    @Size(max = 255, message = "Contact name must be less than 255 characters")
    private String contactName;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone number must be in the format XXX-XXX-XXXX")
    private String phone;

    @NotBlank(message = "Fax number is mandatory")
    private String fax;
}
