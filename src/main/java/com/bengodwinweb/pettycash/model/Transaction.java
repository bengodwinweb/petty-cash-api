package com.bengodwinweb.pettycash.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "transaction")
public class Transaction {

    @Id
    private String id;
    private String paidTo;
    private String expenseType;
    private int amount;
    private String index;
    private String account;
    private String description;
    private LocalDateTime created;
}
