package com.bengodwinweb.pettycash.repository;

import com.bengodwinweb.pettycash.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(@Param("email") String email);
}
