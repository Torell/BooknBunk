package com.example.booknbunk.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "integrationproperties")
@Getter
@Setter
public class IntegrationProperties {


    private BlacklistProperties blacklist;
    private ContractCustomerProperties contractCustomer;
    private EventProperties event;
    private ShipperProperties shipper;

/*
    public BlacklistProperties blacklist() {
        return blacklist;
    }

    public void setBlacklist(BlacklistProperties blacklist) {
        this.blacklist = blacklist;
    }

    public ContractCustomerProperties contractCustomer() {
        return contractCustomer;
    }

    public void setContractCustomer(ContractCustomerProperties contractCustomer) {
        this.contractCustomer = contractCustomer;
    }

    public EventProperties event() {
        return event;
    }

    public void setEvent(EventProperties event) {
        this.event = event;
    }

    public ShipperProperties shipper() {
        return shipper;
    }

    public void setShipper(ShipperProperties shipper) {
        this.shipper = shipper;
    }*/

}
