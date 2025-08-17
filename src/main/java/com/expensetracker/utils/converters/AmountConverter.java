package com.expensetracker.utils.converters;

public interface AmountConverter {
    long convertToCents(String amount);

    String convertFromCents(long cents);
}