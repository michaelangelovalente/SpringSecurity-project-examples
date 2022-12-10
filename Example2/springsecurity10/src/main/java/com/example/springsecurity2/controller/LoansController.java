package com.example.springsecurity2.controller;


import com.example.springsecurity2.model.Loans;
import com.example.springsecurity2.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {


    @Autowired
    LoanRepository loanRepository;

    //load all the loan details for a customer id
    @GetMapping(value={"/myLoans"})
    @PostAuthorize("hasRole('USER')") // no return value.. method will end up being ran, but spring will realize that user does not have authorization.
    public List<Loans> getLoanDetails(@RequestParam int id){
        System.out.println("Id: " + id);
        List<Loans> loans = loanRepository.findByCustomerIdOrderByCreateDtDesc(id);
        if( loans != null){
            return loans;
        }else{
            return null;
        }
    }

}
