package com.bengodwinweb.pettycash.util;

public enum Currency {
    TWENTIES(2000),
    TENS(1000),
    FIVES(500),
    ONES(100),
    QROLLS(1000),
    DROLLS(500),
    NROLLS(200),
    PROLLS(50),
    QUARTERS(25),
    DIMES(10),
    NICKELS(5),
    PENNIES(1);

    private int value;

    Currency(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }


}
