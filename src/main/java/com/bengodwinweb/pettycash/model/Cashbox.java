package com.bengodwinweb.pettycash.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@Document(collection = "cashbox")
public class Cashbox {

    @Id
    private String id;
    private String company;
    private String name;
    private int total;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;
    private int remainingCash;

    public Cashbox() {
        created = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
        transactions = new ArrayList<>();
    }

    public Cashbox(String company, String name, int total) {
        this();
        this.company = company;
        this.name = name;
        this.total = total;
    }

    @DBRef
    private Box currentBox;
    @DBRef
    private Box changeBox;
    @DBRef
    private Box idealBox;
    @DBRef
    private List<Transaction> transactions;
    @DBRef
    private User user;

}
