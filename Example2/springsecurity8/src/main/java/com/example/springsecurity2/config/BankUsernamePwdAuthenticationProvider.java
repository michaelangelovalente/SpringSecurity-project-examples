
// //Custom Authentication provider
package com.example.springsecurity2.config;
import com.example.springsecurity2.model.Authority;
import com.example.springsecurity2.model.Customer;
import com.example.springsecurity2.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class BankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //authentication business logic
            // fetch user details
            // compare passwords
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Customer> customer = customerRepository.findByEmail(username);
        if( customer.size() > 0){
            if(passwordEncoder.matches(pwd, customer.get(0).getPwd())){
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority( customer.get(0).getRole()));
//                return new UsernamePasswordAuthenticationToken(username, pwd, authorities);
                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities( customer.get(0).getAuthorities())); // authorities of a given customer loaded from a db

            }else{
                throw new BadCredentialsException("Invalid password!");
            }
        }else{
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    //helper method: Given the authorities of an end user from DB, creates an object of granted Authority
    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for( Authority authority : authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }

        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //we add support for username, password style authentication
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
