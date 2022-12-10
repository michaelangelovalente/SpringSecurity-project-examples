package com.example.springsecurity2.filter;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAfterFilter implements Filter {


    private final Logger LOG =
            Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // when the user is authenticated, user data is saved in the context.
        if( authentication != null){
            LOG.info("USER " + authentication.getName() + " is successfully authenticated and has authorities " + authentication.getAuthorities());

        }
        chain.doFilter(request,response);
    }
}
