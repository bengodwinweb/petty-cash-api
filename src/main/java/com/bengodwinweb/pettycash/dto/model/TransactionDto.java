package com.bengodwinweb.pettycash.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class TransactionDto {

    private String id;

    @NotNull
    @NotEmpty
    private String paidTo;

    @NotNull
    @NotEmpty
    private String expenseType;

    @NotNull
    @DecimalMin("0.01")
    private Double amount;

    @NotNull
    @NotEmpty
    private String index;

    @NotNull
    @NotEmpty
    private String account;
    private String description;
}
