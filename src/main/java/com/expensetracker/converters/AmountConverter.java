package com.expensetracker.converters;

public interface AmountConverter {
    long convertToCents(String amount);

    String convertFromCents(long cents);
}