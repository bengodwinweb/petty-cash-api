package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.controller.request.UserSignupRequest;
import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.dto.response.SimpleResponse;
import com.bengodwinweb.pettycash.exception.EmailExistsException;
import com.bengodwinweb.pettycash.exception.ValidationException;
import com.bengodwinweb.pettycash.security.JwtResponse;
import com.bengodwinweb.pettycash.security.JwtTokenService;
import com.bengodwinweb.pettycash.security.MongoUserDetailsService;
import com.bengodwinweb.pettycash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/register")
    @ResponseBody
    public SimpleResponse getRegister() {
        System.out.println("GET to /register");
        return new SimpleResponse("get to /register");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody @Valid UserSignupRequest userSignupRequest, BindingResult result, WebRequest req, Errors errors) throws EmailExistsException, ValidationException {

        if (result.hasErrors()) throw new ValidationException("Error validating New User", errors);

        UserDto newUser = null;
        newUser = userService.signup(userSignupRequest);

        if (newUser == null) result.rejectValue("email", "message.regError");

        final UserDetails newUserDetails = userDetailsService.loadUserByUsername(newUser.getEmail());
        final String token = jwtTokenService.generateToken(newUserDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
