package com.bengodwinweb.pettycash.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "user")
public class User implements Comparable<User> {

    @Id
    private String id;

    private String email;

    @JsonIgnore
    private String password;

    private String firstName;
    private String lastName;

    private LocalDateTime created;
    private LocalDateTime lastSeen;

    private String resetPasswordToken;
    private LocalDateTime resetPasswordTokenExpires;

    @DBRef
    private List<Role> roles;

    public User() {
        created = LocalDateTime.now();
        lastSeen = LocalDateTime.now();
    }

    public String getFullName() {
        return firstName.concat(" ").concat(lastName);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    @Override
    public int compareTo(User o) {
        if (this.getLastName().equals(o.getLastName())) return this.firstName.compareTo(o.firstName);
        return this.lastName.compareTo(o.lastName);
    }
}
