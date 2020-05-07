package com.bengodwinweb.pettycash.util;

import java.text.DecimalFormat;

public class MoneyUtil {

    public static final int CENTS_PER_DOLLAR = 100;
    public static final DecimalFormat format = new DecimalFormat("#,###,##0.00");

    public static double centsToDouble(int cents) {
        return ((double) cents) / CENTS_PER_DOLLAR;
    }

    public static int doubleToCents(double value) {
        return (int) (value * CENTS_PER_DOLLAR);
    }
}
