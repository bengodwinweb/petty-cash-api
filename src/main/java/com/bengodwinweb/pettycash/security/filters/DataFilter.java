package com.bengodwinweb.pettycash.security.filters;

import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Component
@Order(2)
public class DataFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        Principal principal = req.getUserPrincipal();

        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName());
            user.setLastSeen(LocalDateTime.now());
            userRepository.save(user);
        }

        chain.doFilter(req, res);
    }
}
