package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.dto.mapper.UserMapper;
import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.exception.UnauthorizedException;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.repository.UserRepository;
import com.bengodwinweb.pettycash.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUtil userUtil;

    @GetMapping("/users")
    public List<UserDto> getAllUsers(Principal principal) throws UnauthorizedException {
        User currentUser = userRepository.findByEmail(principal.getName());
        if (!userUtil.isAdmin(currentUser.getEmail())) throw new UnauthorizedException("Not authorized to view users");
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
