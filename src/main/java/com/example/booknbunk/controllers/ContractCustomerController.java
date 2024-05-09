package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.ContractCustomerDetailedDTO;
import com.example.booknbunk.services.interfaces.ContractCustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/contractCustomer")
public class ContractCustomerController {

    private final ContractCustomerService contractCustomerService;

    public ContractCustomerController(ContractCustomerService contractCustomerService) {
        this.contractCustomerService = contractCustomerService;
    }
    @GetMapping("/getAll")
    public String getAllContractCUstomersWithSearchAndOrder(Model model,
                                                            @RequestParam(defaultValue = "0")int page,
                                                            @RequestParam(defaultValue = "companyName")String sort,
                                                            @RequestParam(defaultValue = "asc")String sortDirection,
                                                            @RequestParam(required = false)String search)
    {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.fromString(sortDirection),sort));
        Page<ContractCustomerDetailedDTO> customerDetailedDTOPage = contractCustomerService.getAllContractCustomerPagesWithSearch(search,pageable);
        model.addAttribute("contractCustomerPages",customerDetailedDTOPage);
        return "allContractCustomers";

    }
}
