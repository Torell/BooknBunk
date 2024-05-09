package com.example.booknbunk;

import com.example.booknbunk.models.ContractCustomer;
import com.example.booknbunk.services.interfaces.ContractCustomerService;
import com.example.booknbunk.utils.ContractCustomerListWrapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@ComponentScan
public class FetchAllContractCustomers implements CommandLineRunner {

    ContractCustomerService contractCustomerService;


    public FetchAllContractCustomers(ContractCustomerService contractCustomerService) {
        this.contractCustomerService = contractCustomerService;
    }

    @Override
    public void run(String...args) throws Exception {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        System.out.println("mapper created");

        ContractCustomerListWrapper allCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), ContractCustomerListWrapper.class);
        System.out.println("mapper reading values...");
        List<ContractCustomer> customers = allCustomers.getCustomers();

        contractCustomerService.createOrUpdateContractCustomers(customers);
        System.out.println("customers saved to database");


    }
}