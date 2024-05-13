package com.example.booknbunk.services.implementations;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.models.ContractCustomer;
import com.example.booknbunk.repositories.ContractCustomerRepository;
import com.example.booknbunk.services.interfaces.ContractCustomerService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
@Service
public class ContractCustomerServiceImplementation implements ContractCustomerService {

    ContractCustomerRepository contractCustomerRepository;
    @Override
    @Transactional
    public void createOrUpdateContractCustomers(List<ContractCustomer> customers) {
        customers.forEach(contractCustomer -> {
            contractCustomerRepository.findById(contractCustomer.getExternalSystemId()).ifPresentOrElse(
                    existingCustomer -> {
                        existingCustomer.setCity(contractCustomer.getCity());
                        existingCustomer.setFax(contractCustomer.getFax());
                        existingCustomer.setCountry(contractCustomer.getCountry());
                        existingCustomer.setContactTitle(contractCustomer.getContactTitle());
                        existingCustomer.setCompanyName(contractCustomer.getCompanyName());
                        existingCustomer.setPhone(contractCustomer.getPhone());
                        contractCustomerRepository.save(existingCustomer);
                    },
                    () ->
                            contractCustomerRepository.save(contractCustomer)
            );
        });
    }

    @Override
    public Page<ContractCustomerDetailedDTO> getAllContractCustomerPages(Pageable pageable) {
        return contractCustomerRepository.findAll(pageable)
                .map(this::contractCustomerToDetailedDTO);
    }
    @Override
    public Page<ContractCustomerDetailedDTO> getAllContractCustomerPagesWithSearch(String search, Pageable pageable) {
        return contractCustomerRepository.findByCompanyNameContainingIgnoreCaseOrCountryContainingIgnoreCaseOrContactNameContainingIgnoreCase(search,search,search,pageable)
                .map(this::contractCustomerToDetailedDTO);
    }
    @Override
    public ContractCustomerDetailedDTO contractCustomerToDetailedDTO(ContractCustomer contractCustomer) {
        return ContractCustomerDetailedDTO.builder()
                .localId(contractCustomer.getLocalId())
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
                .localId(contractCustomerDetailedDTO.getLocalId())
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

    public ContractCustomerServiceImplementation(ContractCustomerRepository contractCustomerRepository) {
        this.contractCustomerRepository = contractCustomerRepository;
    }
}
