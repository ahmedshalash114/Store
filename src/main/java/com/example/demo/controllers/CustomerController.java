package com.example.demo.controllers;

import com.example.demo.DTO.Request.CustomerRequest;
import com.example.demo.DTO.Response.CustomerResponse;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId){
        return new ResponseEntity<> (customerService.getCustomer(customerId), HttpStatus.OK);
    }
    @PostMapping
    public void postCustomer(@RequestBody CustomerRequest request){customerService.postCustomer(request);}

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }
    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable Long customerId,@RequestBody CustomerRequest request){
        customerService.updateCustomer(customerId,request);
    }
}