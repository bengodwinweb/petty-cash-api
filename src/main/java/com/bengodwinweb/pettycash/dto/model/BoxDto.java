package com.bengodwinweb.pettycash.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class BoxDto {

    private String id;

    @NotNull
    @Min(0)
    private Integer twenties;
    private double twentiesValue;

    @NotNull
    @Min(0)
    private Integer tens;
    private double tensValue;

    @Min(0)
    private @NotNull int fives;
    private double fivesValue;

    @NotNull
    @Min(0)
    private Integer ones;
    private double onesValue;

    @NotNull
    @Min(0)
    private Integer qrolls;
    private double qrollsValue;

    @NotNull
    @Min(0)
    private Integer drolls;
    private double drollsValue;

    @NotNull
    @Min(0)
    private Integer nrolls;
    private double nrollsValue;

    @NotNull
    @Min(0)
    private Integer prolls;
    private double prollsValue;

    @NotNull
    @Min(0)
    private Integer quarters;
    private double quartersValue;

    @NotNull
    @Min(0)
    private Integer dimes;
    private double dimesValue;

    @NotNull
    @Min(0)
    private Integer nickels;
    private double nickelsValue;

    @NotNull
    @Min(0)
    private Integer pennies;
    private double penniesValue;

    private double boxTotal;
}

