package com.example.springsecurity2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.Entity;

@SpringBootApplication
//@EnableWebSecurity(debug=true)
public class Springsecurity2Application {
    public static void main(String[] args) {
        SpringApplication.run(Springsecurity2Application.class, args);
    }

}
