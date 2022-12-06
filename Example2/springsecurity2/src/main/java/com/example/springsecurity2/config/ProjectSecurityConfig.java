package com.example.springsecurity2.config;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    // method was taken from default security configuration ( class SpringBootWebSecurityConfiguration )
    @Bean
//    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest()
//        .authenticated(); // specifies to protect the previously declared path requests
//        http.formLogin();
//        http.httpBasic();


        /*
        *
        * Below is the custom security configurations
        * */
        http.authorizeRequests()
                .antMatchers("/myAccount", "myBalance", "myLoans", "myCards").authenticated()
                .antMatchers( "/notices", "/contacts").permitAll()
                .and().formLogin()
                .and().httpBasic();


        /**
         *
         * Below denies all requests
         * */
//        http.authorizeRequests()
//                .anyRequest().denyAll()
//                .and().formLogin()
//                .and().httpBasic();


        /**
         *
         *This configuration permits all requests
         **/
//        http.authorizeRequests()
//                .anyRequest().permitAll()
//                .and().formLogin()
//                .and().httpBasic();

        return http.build();
    }

}
