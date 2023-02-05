package com.example.springsecurity2.controller;

import com.example.springsecurity2.model.Accounts;
import com.example.springsecurity2.model.Customer;
import com.example.springsecurity2.repository.AccountsRepository;
import com.example.springsecurity2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;


    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email){
       List<Customer> customers = customerRepository.findByEmail(email);
       if( customers != null && !customers.isEmpty()){
           Accounts accounts = accountsRepository.findByCustomerId(customers.get(0).getId());
           if( accounts !=null) return accounts;
       }

       return null;
    }

}
