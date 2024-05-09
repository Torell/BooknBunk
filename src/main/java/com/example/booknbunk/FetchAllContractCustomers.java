package com.example.booknbunk;

import com.example.booknbunk.utils.ContractCustomerListWrapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;

import java.net.MalformedURLException;
import java.net.URL;

public class FetchAllContractCustomers implements CommandLineRunner {

    @Override
    public void run(String...args) throws Exception {

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper xmlMapper = new XmlMapper(module);

        ContractCustomerListWrapper allCustomers = xmlMapper.readValue(new URL("https://javaintegration.systementor.se/customers"), ContractCustomerListWrapper.class);


    }
}
