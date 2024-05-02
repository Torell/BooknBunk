package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.services.interfaces.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping("/customers")
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @GetMapping("/addCustomerPage")
    public String addCustomerPage() {
        return "/customer/addCustomer";
    }

    @GetMapping("/editCustomerPage")
    public String editCustomerPage(@RequestParam("id") long id, Model model) {
        CustomerDetailedDto customer = customerService.findCustomerById(id);
        model.addAttribute("customerId", id);
        return "/customer/editCustomer";
    }

 @RequestMapping("/deleteCustomer/{id}") // den nya som returnerar response!
 public String deleteCustomerById(@PathVariable long id, RedirectAttributes redirectAttributes){
     String message = customerService.deleteCustomer(id);
     redirectAttributes.addFlashAttribute("message", message);
     return "redirect:/customers/all";
 }


    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam("name") String name, @RequestParam("email") String email) {
        customerService.createCustomer(name, email);
        return "redirect:/customers/all";
    }



    @RequestMapping("/updateCustomer")
    public String updateCustomer(CustomerDetailedDto customerDetailedDto) {
        customerService.createCustomer(customerDetailedDto);

        return "redirect:/customers/all";
    }


    @RequestMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable long id, Model model) {
        CustomerDetailedDto customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("bookingList",customer.getBookingMiniDtoList());
        return "/customer/editCustomer"; // Korrekt?
    }


    @RequestMapping("/all")
    public String getAllCustomers(Model model) {
        List<CustomerDetailedDto> customerDetailedDtoList = customerService.getAllCustomersDetailedDto();
        model.addAttribute("allCustomers", customerDetailedDtoList);
        return "/customer/allCustomersWithDeleteAndEdit";
    }


}
