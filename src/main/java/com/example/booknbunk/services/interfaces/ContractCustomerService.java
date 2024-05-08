package com.example.booknbunk.services.interfaces;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.models.ContractCustomer;

public interface ContractCustomerService {

    ContractCustomerDetailedDTO contractCustomerToDetailedDTO(ContractCustomer contractCustomer);
    ContractCustomer detailedDtotoContractCustomer(ContractCustomerDetailedDTO contractCustomerDetailedDTO);

}
