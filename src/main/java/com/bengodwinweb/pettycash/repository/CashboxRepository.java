package com.bengodwinweb.pettycash.repository;

import com.bengodwinweb.pettycash.model.Cashbox;
import com.bengodwinweb.pettycash.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashboxRepository extends MongoRepository<Cashbox, String> {
    List<Cashbox> findByUser(@Param("user") User user);
}
