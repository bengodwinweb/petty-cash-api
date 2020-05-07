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
    private int twenties;
    private double twentiesValue;

    @NotNull
    @Min(0)
    private int tens;
    private double tensValue;

    @NotNull
    @Min(0)
    private int fives;
    private double fivesValue;

    @NotNull
    @Min(0)
    private int ones;
    private double onesValue;

    @NotNull
    @Min(0)
    private int qrolls;
    private double qrollsValue;

    @NotNull
    @Min(0)
    private int drolls;
    private double drollsValue;

    @NotNull
    @Min(0)
    private int nrolls;
    private double nrollsValue;

    @NotNull
    @Min(0)
    private int prolls;
    private double prollsValue;

    @NotNull
    @Min(0)
    private int quarters;
    private double quartersValue;

    @NotNull
    @Min(0)
    private int dimes;
    private double dimesValue;

    @NotNull
    @Min(0)
    private int nickels;
    private double nickelsValue;

    @NotNull
    @Min(0)
    private int pennies;
    private double penniesValue;

    private double boxTotal;
}

