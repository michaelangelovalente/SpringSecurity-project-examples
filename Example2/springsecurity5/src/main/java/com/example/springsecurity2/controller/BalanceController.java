package com.example.springsecurity2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Responsible for info about user transactions
// and balance details.
@RestController
public class BalanceController {


    @GetMapping("/myBalance")
    public String getBalanceDetails(){
        return "Here are the balance details from the DB.";
    }

}
