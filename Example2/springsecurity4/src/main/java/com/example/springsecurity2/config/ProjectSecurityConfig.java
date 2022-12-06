package com.example.springsecurity2.config;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
                .antMatchers("/myAccount", "myBalance", "myLoans", "myCards").authenticated()
                .antMatchers( "/notices", "/contacts", "/register").permitAll()
                .and().formLogin()
                .and().httpBasic();

        return http.build();
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(){
//
//        /*Appr 1: use Def pass encoder*/
////        UserDetails admin = User.withDefaultPasswordEncoder()
////                .username("admin")
////                .password("12345")
////                .authorities("admin")
////                .build();
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("12345")
////                .authorities("read")
////                .build();
////
////
////        return new InMemoryUserDetailsManager( admin, user);
//
//        /*Appr 2 we use NoOpPasswordEncoder Bean*/
//        UserDetails admin = User.withUsername("admin")
//                .password("12345")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password("12345")
//                .authorities("read")
//                .build();
//
//
//        return new InMemoryUserDetailsManager( admin, user);
//
//    }

//    // Spring Security recognizes jdbc style authentication
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }



    //this is always needed cause it communicates to spring security how our passwords are stored
    /**
     *
     * NoOpPassWncoder is not recommended for production
     *
     * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

}
