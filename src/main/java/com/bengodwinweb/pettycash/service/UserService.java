package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.controller.request.UserSignupRequest;
import com.bengodwinweb.pettycash.dto.mapper.UserMapper;
import com.bengodwinweb.pettycash.dto.model.UserDto;
import com.bengodwinweb.pettycash.exception.EmailExistsException;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.model.Role;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.model.UserRoles;
import com.bengodwinweb.pettycash.repository.RoleRepository;
import com.bengodwinweb.pettycash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) throw new NotFoundException("User with id " + id + " not found");
        User user = optionalUser.get();
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundException("User with email " + email + " not found");
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws NotFoundException {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) throw new NotFoundException("User with email " + userDto.getEmail() + " not found");
        user
                .setEmail(userDto.getEmail())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName());

        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto deleteUserById(String id) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) throw new NotFoundException("User with id " + id + " not found");
        User user = optionalUser.get();
        userRepository.delete(user);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto signup(UserSignupRequest userSignupRequest) throws EmailExistsException {
        if (emailExists(userSignupRequest.getEmail())) throw new EmailExistsException();

        Role userRole = roleRepository.findByRole(UserRoles.USER.name());
        System.out.println(userRole);

        User user = new User()
                .setEmail(userSignupRequest.getEmail())
                .setPassword(passwordEncoder.encode(userSignupRequest.getPassword()))
                .setRoles(Arrays.asList(userRole))
                .setFirstName(userSignupRequest.getFirstName())
                .setLastName(userSignupRequest.getLastName());

        return UserMapper.toUserDto(userRepository.save(user));
    }

    public boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

}
