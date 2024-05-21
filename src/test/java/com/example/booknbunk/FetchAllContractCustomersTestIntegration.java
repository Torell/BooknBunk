package com.example.booknbunk;

import com.example.booknbunk.models.ContractCustomer;
import com.example.booknbunk.repositories.ContractCustomerRepository;
import com.example.booknbunk.services.interfaces.ContractCustomerService;
import com.example.booknbunk.utils.ContractCustomerListWrapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
class FetchAllContractCustomersTestIntegration {

    @Autowired
    private ContractCustomerService contractCustomerService;

    @Autowired
    private ContractCustomerRepository contractCustomerRepository;

    private FetchAllContractCustomers fetchAllContractCustomers;
    @BeforeEach
    void setUp() {
        fetchAllContractCustomers = new FetchAllContractCustomers(contractCustomerService);
    }
    @Test
    void run() throws Exception {

        try (InputStream xmlFile = getClass().getClassLoader().getResourceAsStream("contractCustomer.xml")) {

            assertThat(xmlFile).isNotNull();

            JacksonXmlModule module = new JacksonXmlModule();
            module.setDefaultUseWrapper(false);
            XmlMapper xmlMapper = new XmlMapper(module);
            ContractCustomerListWrapper allCustomers = xmlMapper.readValue(xmlFile, ContractCustomerListWrapper.class);

            List<ContractCustomer> customers = allCustomers.getCustomers();

            contractCustomerService.createOrUpdateContractCustomers(customers);

            assertThat(contractCustomerRepository.count()).isEqualTo(customers.size());

            for (ContractCustomer customer : customers) {
                ContractCustomer savedCustomer = contractCustomerRepository.findById(customer.getLocalId()).orElse(null);
                assertThat(savedCustomer).isNotNull();
                assertThat(savedCustomer.getExternalSystemId()).isEqualTo(customer.getExternalSystemId());
                assertThat(savedCustomer.getCompanyName()).isEqualTo(customer.getCompanyName());
                assertThat(savedCustomer.getContactTitle()).isEqualTo(customer.getContactTitle());
                assertThat(savedCustomer.getStreetAddress()).isEqualTo(customer.getStreetAddress());
                assertThat(savedCustomer.getCity()).isEqualTo(customer.getCity());
                assertThat(savedCustomer.getPostalCode()).isEqualTo(customer.getPostalCode());
                assertThat(savedCustomer.getCountry()).isEqualTo(customer.getCountry());
                assertThat(savedCustomer.getContactName()).isEqualTo(customer.getContactName());
                assertThat(savedCustomer.getPhone()).isEqualTo(customer.getPhone());
                assertThat(savedCustomer.getFax()).isEqualTo(customer.getFax());
            }
        }

    }

    @Test
    void XMLPropertiesSet() throws IOException {


        try (InputStream xmlFile = getClass().getClassLoader().getResourceAsStream("contractCustomer.xml")) {

            assert xmlFile != null;

            Scanner scanner = new Scanner(xmlFile).useDelimiter("\\A");
            String result = scanner.hasNext() ? scanner.next() : "";
            assertTrue(result.contains("<customers>"));
            assertTrue(result.contains("</customers>"));
            assertTrue(result.contains("<id>"));
            assertTrue(result.contains("</id>"));
            assertTrue(result.contains("<companyName>"));
            assertTrue(result.contains("</companyName>"));
            assertTrue(result.contains("<contactName>"));
            assertTrue(result.contains("</contactName>"));
            assertTrue(result.contains("<contactTitle>"));
            assertTrue(result.contains("</contactTitle>"));
            assertTrue(result.contains("<streetAddress>"));
            assertTrue(result.contains("</streetAddress>"));
            assertTrue(result.contains("<city>"));
            assertTrue(result.contains("</city>"));
            assertTrue(result.contains("<postalCode>"));
            assertTrue(result.contains("</postalCode>"));
            assertTrue(result.contains("<country>"));
            assertTrue(result.contains("</country>"));
            assertTrue(result.contains("<phone>"));
            assertTrue(result.contains("</phone>"));
            assertTrue(result.contains("<fax>"));
            assertTrue(result.contains("</fax>"));
        }
    }
}