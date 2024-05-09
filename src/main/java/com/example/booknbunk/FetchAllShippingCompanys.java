package com.example.booknbunk;

import com.example.booknbunk.models.ContractCustomer;
import com.example.booknbunk.services.interfaces.ShippingService;
import com.example.booknbunk.utils.ContractCustomerListWrapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.net.URL;
import java.util.List;

@ComponentScan
public class FetchAllShippingCompanys implements CommandLineRunner {

    ShippingService shippingService;

    public FetchAllShippingCompanys(ShippingService shippingService){
        this.shippingService = shippingService;
    }

    @Override
    public void run(String... args) throws Exception {


    }
}
