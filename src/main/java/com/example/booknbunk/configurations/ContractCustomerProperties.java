package com.example.booknbunk.configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "integrations.contract-customer")
@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractCustomerProperties {
    private String url;
}

