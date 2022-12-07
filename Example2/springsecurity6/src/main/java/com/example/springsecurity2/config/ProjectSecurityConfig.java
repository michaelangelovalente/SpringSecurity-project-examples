package com.example.springsecurity2.config;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

    // method was taken from default security configuration ( class SpringBootWebSecurityConfiguration )
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        //.csrf().disable() --> disables csrf, which is enabled by default in Spring
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/myAccount", "myBalance", "myLoans", "myCards","/user").authenticated()
                .antMatchers( "/notices", "/contacts", "/register").permitAll()
                .and().formLogin()
                .and().httpBasic();

        return http.build();
    }



    //this is always needed cause it communicates to spring security how our passwords are stored
    /**
     * NoOpPassWncoder is not recommended for production
     *
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(); // password encoder leverages Bcrypt hashing algorithm
    }

}
