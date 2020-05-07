package com.bengodwinweb.pettycash.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CashboxDto {

    private String id;

    @NotNull
    @NotEmpty
    private String company;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Min(100)
    private double total;

    @NotNull
    @Min(1)
    private double remainingCash;
}
