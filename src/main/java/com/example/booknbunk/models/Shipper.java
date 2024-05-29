package com.example.booknbunk.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Company name is mandatory")
    private String companyName;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Phone number must be in the format XXX-XXX-XXXX")
    private String phone;

    public Shipper(Long id, String companyName, String phone) {
        this.id = id;
        this.companyName = companyName;
        this.phone = phone;
    }
}
