package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.models.Customer;
import com.example.booknbunk.services.interfaces.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    //private static final Logger log = LoggerFactory.getLogger(CustomerController.class); vad gör denna?/ Steffi :)

    @Autowired
    private CustomerService customerService;

    @GetMapping("/addCustomerPage")
    public String addCustomerPage() {
        return "addCustomer.html";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam("name") String name, @RequestParam("email") String email) {
        CustomerDetailedDto customer = new CustomerDetailedDto();
        customer.setName(name);
        customer.setEmail(email);
        customerService.createCustomer(customer);
        return "redirect:/all"; // När man trycker på knappen så omdirigeras man tillbaka till alla kunder
    }

    @RequestMapping("/all")
    public String getAllCustomers(Model model){
        List<CustomerDetailedDto> customerDetailedDtoList = customerService.getAllCustomersDetailedDto();

        // Logga storleken på listan
        System.out.println("Antal kunder hämtade: " + customerDetailedDtoList.size());

        // Logga information om varje kund i listan
        for (CustomerDetailedDto customer : customerDetailedDtoList) {
            System.out.println("Kund ID: " + customer.getId() + ", Namn: " + customer.getName() + ", Email: " + customer.getEmail());
        }

        model.addAttribute("allCustomers", customerDetailedDtoList);
        return "allCustomersWithDeleteAndEdit.html";
    }


    //getAll - Klart
    //add
    //upDate
    //delete

   /* @PostMapping("/add")
    public void addCustomer(@RequestBody CustomerDetailedDto customerDetailedDto){
        customerService.createCustomer(customerDetailedDto);
    }*/





}
