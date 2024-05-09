package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.models.ContractCustomer;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ContractCustomerService {


    void createOrUpdateContractCustomers(List<ContractCustomer> customers);

    ContractCustomerDetailedDTO contractCustomerToDetailedDTO(ContractCustomer contractCustomer);
    ContractCustomer detailedDtotoContractCustomer(ContractCustomerDetailedDTO contractCustomerDetailedDTO);

}
