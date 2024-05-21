package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.models.ContractCustomer;
import com.example.booknbunk.repositories.ContractCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContractCustomerServiceImplementationTest {


    private ContractCustomerRepository contractCustomerRepository = mock(ContractCustomerRepository.class);
    private ContractCustomerServiceImplementation contractCustomerService;

    private ContractCustomer c1 = new ContractCustomer(
                null,
                        1001L,
                        "Tech Solutions Ltd.",
                        "Mr.",
                        "123 Tech Street",
                        "Tech City",
                        12345,
                        "Techland",
                        "John Doe",
                        "123-456-7890",
                        "123-456-7891"
    );
                private ContractCustomer c2 = new ContractCustomer(
                        null,
                                1002L,
                                "Innovative Creations Inc.",
                                "Ms.",
                                "456 Creative Blvd",
                                "Innovation Town",
                                67890,
                                "Creativia",
                                "Jane Smith",
                                "234-567-8901",
                                "234-567-8902"
    );

    @BeforeEach
    void setUp() {
        contractCustomerService = new ContractCustomerServiceImplementation(contractCustomerRepository);
    }

    @Test
    void createContractCustomers() {
        List<ContractCustomer> listOfContractCustomer = List.of(c1,c2);

        contractCustomerService.createOrUpdateContractCustomers(listOfContractCustomer);
        verify(contractCustomerRepository,times(1)).save(argThat(contractCustomer -> contractCustomer.getExternalSystemId() == 1001L));
        verify(contractCustomerRepository,times(1)).save(argThat(contractCustomer -> contractCustomer.getExternalSystemId() == 1002L));
    }

    @Test
    void getAllContractCustomerPagesWithSearch() {
        List<ContractCustomer> listOfCustomers = List.of(c1,c2);
        Page<ContractCustomer> customerPage = new PageImpl<>(listOfCustomers);

        when(contractCustomerRepository.findByCompanyNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrContactNameContainingIgnoreCase(anyString(),anyString(),anyString(),any(Pageable.class)))
                .thenReturn(customerPage);

        Pageable pageable = PageRequest.of(0,10);

        Page<ContractCustomerDetailedDTO> result = contractCustomerService.getAllContractCustomerPagesWithSearch("tech",pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals("Tech City", result.getContent().get(0).getCity());
        assertEquals("Tech Solutions Ltd.", result.getContent().get(0).getCompanyName());
        assertEquals("Innovation Town", result.getContent().get(1).getCity());
        assertEquals("Innovative Creations Inc.", result.getContent().get(1).getCompanyName());

    }
    @Test
    void contractCustomerToDetailedDTO() {
        ContractCustomerDetailedDTO c1Dto = contractCustomerService.contractCustomerToDetailedDTO(c1);

        assertEquals(c1.getCity(), c1Dto.getCity());
        assertEquals(c1.getCompanyName(), c1Dto.getCompanyName());
        assertEquals(c1.getContactTitle(), c1Dto.getContactTitle());
        assertEquals(c1.getStreetAddress(), c1Dto.getStreetAddress());
        assertEquals(c1.getPostalCode(), c1Dto.getPostalCode());
        assertEquals(c1.getCountry(), c1Dto.getCountry());
        assertEquals(c1.getContactName(), c1Dto.getContactName());
        assertEquals(c1.getPhone(), c1Dto.getPhone());
        assertEquals(c1.getFax(), c1Dto.getFax());
        assertEquals(c1.getExternalSystemId(), c1Dto.getExternalSystemId());

    }

    @Test
    void detailedDtoToContractCustomer() {
        ContractCustomerDetailedDTO c1Dto = contractCustomerService.contractCustomerToDetailedDTO(c1);
        ContractCustomer c1Converted = contractCustomerService.detailedDtotoContractCustomer(c1Dto);

        assertEquals(c1Converted.getCity(), c1Dto.getCity());
        assertEquals(c1Converted.getCompanyName(), c1Dto.getCompanyName());
        assertEquals(c1Converted.getContactTitle(), c1Dto.getContactTitle());
        assertEquals(c1Converted.getStreetAddress(), c1Dto.getStreetAddress());
        assertEquals(c1Converted.getPostalCode(), c1Dto.getPostalCode());
        assertEquals(c1Converted.getCountry(), c1Dto.getCountry());
        assertEquals(c1Converted.getContactName(), c1Dto.getContactName());
        assertEquals(c1Converted.getPhone(), c1Dto.getPhone());
        assertEquals(c1Converted.getFax(), c1Dto.getFax());
        assertEquals(c1Converted.getExternalSystemId(), c1Dto.getExternalSystemId());

    }
}