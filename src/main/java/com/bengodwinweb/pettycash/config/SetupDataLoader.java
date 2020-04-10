package com.bengodwinweb.pettycash.config;

import com.bengodwinweb.pettycash.model.Role;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.model.UserRoles;
import com.bengodwinweb.pettycash.repository.RoleRepository;
import com.bengodwinweb.pettycash.repository.UserRepository;
import com.bengodwinweb.pettycash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup || userService.emailExists("user@example.com")) return;

        System.out.println("Performing data setup");

        Role adminRole = createRoleIfNotFound(UserRoles.ADMIN.name());
        System.out.println(adminRole);
        Role userRole = createRoleIfNotFound(UserRoles.USER.name());
        System.out.println(userRole);

        User user = new User()
                .setFirstName("User")
                .setLastName("Test")
                .setPassword(passwordEncoder.encode("password"))
                .setEmail("user@example.com")
                .setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        User admin = new User()
                .setFirstName("Admin")
                .setLastName("Test")
                .setPassword(passwordEncoder.encode("password"))
                .setEmail("admin@example.com")
                .setRoles(Arrays.asList(userRole, adminRole));
        userRepository.save(admin);

        alreadySetup = true;
    }

    @Transactional
    protected Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByRole(name);
        if (role == null) {
            role = new Role().setRole(name);
            roleRepository.save(role);
        }
        return role;
    }
}
