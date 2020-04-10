package com.bengodwinweb.pettycash.security.filters;

import com.bengodwinweb.pettycash.exception.CustomExpiredJwtException;
import com.bengodwinweb.pettycash.security.JwtTokenService;
import com.bengodwinweb.pettycash.security.MongoUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException, IllegalArgumentException {
        final String requestTokenHeder = req.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeder != null && requestTokenHeder.startsWith("Bearer ")) {
            jwtToken = requestTokenHeder.substring(7);
            try {
                username = jwtTokenService.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("couldn't parse username from jwtToken");
            } catch (ExpiredJwtException e) {
                expiredJwtException();
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(req, res);
    }

    private void expiredJwtException() throws CustomExpiredJwtException {
        throw new CustomExpiredJwtException();
    }
}
