package com.bengodwinweb.pettycash.service;

import com.bengodwinweb.pettycash.dto.model.NewUserDto;
import com.bengodwinweb.pettycash.exception.EmailExistsException;
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

@Service
public class UserService implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private ModelMapper modelMapper;

    @Transactional
    @Override
    public User signup(NewUserDto newUserDto) throws EmailExistsException {
        if (emailExists(newUserDto.getEmail())) throw new EmailExistsException();

        Role userRole = roleRepository.findByRole(UserRoles.USER.name());
        System.out.println(userRole);

        User user = new User()
                .setEmail(newUserDto.getEmail())
                .setPassword(passwordEncoder.encode(newUserDto.getPassword()))
                .setRoles(Arrays.asList(userRole))
                .setFirstName(newUserDto.getFirstName())
                .setLastName(newUserDto.getLastName());

        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

}
