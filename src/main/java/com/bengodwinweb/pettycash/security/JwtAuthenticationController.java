package com.bengodwinweb.pettycash.security;

import com.bengodwinweb.pettycash.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest req) throws AuthenticationException {
        System.out.println("POST to /api/authenticate");

        authenticate(req.getUsername(), req.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());
        System.out.printf("UserDetails:\n\tUser - %s\n\tRoles - %s", userDetails.getUsername(), userDetails.getAuthorities());
        final String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled");
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials");
        }
    }
}
