package com.bengodwinweb.pettycash.repository;

import com.bengodwinweb.pettycash.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {}
