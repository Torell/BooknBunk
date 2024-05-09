package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.models.ContractCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ContractCustomerService {


    void createOrUpdateContractCustomers(List<ContractCustomer> customers);


    Page<ContractCustomerDetailedDTO> getAllContractCustomerPages(Pageable pageable);

    Page<ContractCustomerDetailedDTO> getAllContractCustomerPagesWithSearch(String search, Pageable pageable);

    ContractCustomerDetailedDTO contractCustomerToDetailedDTO(ContractCustomer contractCustomer);
    ContractCustomer detailedDtotoContractCustomer(ContractCustomerDetailedDTO contractCustomerDetailedDTO);

}
