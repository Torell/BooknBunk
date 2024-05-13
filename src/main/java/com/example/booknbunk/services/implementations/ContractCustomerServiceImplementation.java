package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.models.ContractCustomer;
import com.example.booknbunk.services.interfaces.ContractCustomerService;
import org.springframework.stereotype.Service;

@Service
public class ContractCustomerServiceImplementation implements ContractCustomerService {

    @Override
    public ContractCustomerDetailedDTO contractCustomerToDetailedDTO(ContractCustomer contractCustomer) {
        return ContractCustomerDetailedDTO.builder()
                .id(contractCustomer.getId())
                .externalSystemId(contractCustomer.getExternalSystemId())
                .companyName(contractCustomer.getCompanyName())
                .contactTitle(contractCustomer.getContactTitle())
                .streetAddress(contractCustomer.getStreetAddress())
                .city(contractCustomer.getCity())
                .postalCode(contractCustomer.getPostalCode())
                .country(contractCustomer.getCountry())
                .contactName(contractCustomer.getContactName())
                .phone(contractCustomer.getPhone())
                .fax(contractCustomer.getFax())
                .build();
    }

    @Override
    public ContractCustomer detailedDtotoContractCustomer(ContractCustomerDetailedDTO contractCustomerDetailedDTO) {
        return ContractCustomer.builder()
                .id(contractCustomerDetailedDTO.getId())
                .externalSystemId(contractCustomerDetailedDTO.getExternalSystemId())
                .companyName(contractCustomerDetailedDTO.getCompanyName())
                .contactTitle(contractCustomerDetailedDTO.getContactTitle())
                .streetAddress(contractCustomerDetailedDTO.getStreetAddress())
                .city(contractCustomerDetailedDTO.getCity())
                .postalCode(contractCustomerDetailedDTO.getPostalCode())
                .country(contractCustomerDetailedDTO.getCountry())
                .contactName(contractCustomerDetailedDTO.getContactName())
                .phone(contractCustomerDetailedDTO.getPhone())
                .fax(contractCustomerDetailedDTO.getFax())
                .build();
    }
}
