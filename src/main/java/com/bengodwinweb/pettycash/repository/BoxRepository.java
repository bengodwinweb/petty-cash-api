package com.bengodwinweb.pettycash.repository;


import com.bengodwinweb.pettycash.model.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends MongoRepository<Box, String> {


}
