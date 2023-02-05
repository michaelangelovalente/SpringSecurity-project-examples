package com.example.springsecurity2.controller;


import com.example.springsecurity2.model.Cards;
import com.example.springsecurity2.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;


    @GetMapping("/myCards")
    public List<Cards> getAccountDetails(@RequestParam int id){
        List<Cards> cards = cardsRepository.findByCustomerId(id);
        if( cards != null){
            return cards;
        }else{
            return null;
        }

    }

}
