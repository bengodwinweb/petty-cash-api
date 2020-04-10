package com.bengodwinweb.pettycash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PettyCashApplication {

	public static void main(String[] args) {
		SpringApplication.run(PettyCashApplication.class, args);
	}

}
