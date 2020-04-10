package com.bengodwinweb.pettycash.util;

import com.bengodwinweb.pettycash.model.Role;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.model.UserRoles;
import com.bengodwinweb.pettycash.repository.RoleRepository;
import com.bengodwinweb.pettycash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email);
        ArrayList<String> roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toCollection(ArrayList::new));
        boolean isAdmin = roles.contains(UserRoles.ADMIN.name());
        System.out.println("User is admin: " + isAdmin);
        return isAdmin;
    }
}
