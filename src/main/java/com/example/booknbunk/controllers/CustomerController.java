package com.example.booknbunk.controllers;

import com.example.booknbunk.dtos.CustomerDetailedDto;
import com.example.booknbunk.services.interfaces.CustomerService;
import lombok.AllArgsConstructor;
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

    @GetMapping("/editCustomerPage")
    public String editCustomerPage(@RequestParam("id") long id, Model model) {
        CustomerDetailedDto customer = customerService.findCustomerById(id);
        model.addAttribute("customerId", id);
        return "editCustomer.html";
    }

 /*  @RequestMapping("/deleteCustomer/{id}")
   public String deleteCustomerById(@PathVariable long id, Model model){
        customerService.deleteCustomer(id);
        List<CustomerDetailedDto> listOfCustomers = customerService.getAllCustomersDetailedDto();
        model.addAttribute("allCustomers", listOfCustomers);
        return "redirect:/customers/all";
   }
*/
    @RequestMapping("/deleteCustomer/{id}")
    public String deleteCustomerById(@PathVariable long id, Model model){
        System.out.println("kommit in i metoden");
        customerService.deleteCustomer(id);
        System.out.println("Kund raderad");
        return "redirect:/customers/all"; // Omdirigera till sidan för att visa alla kunder
    }


    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam("name") String name, @RequestParam("email") String email) {
        customerService.createCustomer(name, email);
        return "redirect:/customers/all";
    }


    @PutMapping("/updateCustomer/{id}")
    public String updateCustomer(@PathVariable long id, @RequestParam("name") String name, @RequestParam("email") String email) {
        customerService.updateCustomer(id, name, email);
        return "redirect:/customers/all";
    }


    @PutMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable long id, Model model) {
        CustomerDetailedDto customer = customerService.findCustomerById(id);
        model.addAttribute("customer", customer);
        return "editCustomer.html"; // Korrekt?
    }


    @RequestMapping("/all")
    public String getAllCustomers(Model model) {
        List<CustomerDetailedDto> customerDetailedDtoList = customerService.getAllCustomersDetailedDto();
        model.addAttribute("allCustomers", customerDetailedDtoList);
        return "allCustomersWithDeleteAndEdit.html";
    }


}
