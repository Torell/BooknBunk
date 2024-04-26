package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @RequestMapping("/all")
    public String getAllCustomers(Model model){
        List<CustomerDetailedDto> customerDetailedDtoList = customerService.getAllCustomers();
        model.addAttribute("allCustomers", customerDetailedDtoList);
        return "allCustomersWithDeleteAndEdit.html";
    }

    //getAll - Klart
    //add
    //upDate
    //delete

    @PostMapping("add")
    public void addCustomer(@RequestBody CustomerDetailedDto customerDetailedDto){
        customerService.createCustomer(customerDetailedDto);
    }





}
