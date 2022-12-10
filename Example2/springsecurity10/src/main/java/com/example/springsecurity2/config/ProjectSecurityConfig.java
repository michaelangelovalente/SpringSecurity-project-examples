package com.example.springsecurity2.config;


import com.example.springsecurity2.filter.AuthoritiesLoggingAfterFilter;
import com.example.springsecurity2.filter.JWTTokenGeneratorFilter;
import com.example.springsecurity2.filter.JWTTokenValidatorFilter;
import com.example.springsecurity2.filter.RequestValidationBeforeFilter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;


@Configuration
public class ProjectSecurityConfig {

    // method was taken from default security configuration ( class SpringBootWebSecurityConfiguration )
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {


        CorsConfigurationSource configurationSource = new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins( Collections.singletonList("http://127.0.0.1:5500")); // allows CORS communication
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L); // 1 hr
                return config;
            }
        };


        //.csrf().disable() --> disables csrf, which is enabled by default in Spring
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Session management config. -->  JSESSIONID will not be generated --> manual token managmnt.
                .and()
                .cors().configurationSource(configurationSource)// cors related configuration

//              .and().csrf().disable() // --> deactivates CSRF --> NOT RECOMMENDED
                .and().csrf().ignoringAntMatchers("/register", "/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // this allows clients to read the cookie with JS
                .and().addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class) // Custom filter before BasicAuthentication filter
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)// Custom filter after BasicAuthentication filter
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class) // generate token after authentication --> this is done only during login process
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class) // generate token before authentication ( requires a JWT TOKEN IN REQUEST ) --> done with every api except login
                .authorizeHttpRequests()


                .antMatchers("/myAccount").hasRole("USER")
                .antMatchers("/myBalance").hasAnyRole("USER","ADMIN")
                .antMatchers("/myLoans").authenticated()
                .antMatchers("/myCards").hasRole("USER")

//                .antMatchers("/myAccount", "myBalance", "myLoans", "myCards","/user").authenticated()
                .antMatchers("/user").authenticated()
                .antMatchers( "/notices", "/register").permitAll()
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
