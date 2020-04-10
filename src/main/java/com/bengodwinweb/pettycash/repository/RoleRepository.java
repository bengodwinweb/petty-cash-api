package com.bengodwinweb.pettycash.repository;

import com.bengodwinweb.pettycash.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByRole(String role);
}
