package com.example.springsecurity2.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.springsecurity2.model.Customer;
import com.example.springsecurity2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){
        Customer savedCustomer = null;
        ResponseEntity response = null;

        try{
            // hash password saved in db
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            System.out.println(customer);
            savedCustomer = customerRepository.save(customer);
            if( savedCustomer.getId() > 0){
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            }
        }catch(Exception ex){
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An Exception occurred due to " + ex.getMessage());
        }

        return response;
    }



    @RequestMapping("/user") // login --> TODO:REPLACE WITH POST
    public Customer getUserDetailsAfterLogin(Authentication authentication){
        System.out.println("Inside user");
        List<Customer> customers = customerRepository.findByEmail(authentication.getName());
        if( customers.size() > 0){
            System.out.println(customers.get(0));
            return customers.get(0);
        }else{
            return null;
        }

    }

}
