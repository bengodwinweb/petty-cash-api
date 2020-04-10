package com.bengodwinweb.pettycash.security;

import com.bengodwinweb.pettycash.model.Role;
import com.bengodwinweb.pettycash.model.User;
import com.bengodwinweb.pettycash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email of " + email + " not found");
        }

//        List<GrantedAuthority> userAuthorities = getUserAuthority(user.getRoles());
//        System.out.println("User has authorities:");
//        for (GrantedAuthority authority : userAuthorities) {
//            System.out.println("\t" + authority);
//        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getUserAuthority(user.getRoles()));
    }

    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
