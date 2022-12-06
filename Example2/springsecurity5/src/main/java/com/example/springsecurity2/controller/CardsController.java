package com.example.springsecurity2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {


    @GetMapping("/myCards")
    public String getAccountDetails(){
        return "Here are the loans.";
    }

}
