package com.expensetracker.converters;

public class DecimalAmountConverter implements AmountConverter {

    @Override
    public long convertToCents(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("Amount cannot be null or empty");
        }

        try {
            double decimal = Double.parseDouble(amount.replace(",", "."));
            return (long) (decimal * 100);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format: " + amount, e);
        }
    }

    @Override
    public String convertFromCents(long cents) {
        if (cents < 0) {
            throw new IllegalArgumentException("Cents cannot be negative");
        }

        long decimal = cents / 100;
        return Long.toString(decimal);
    }
}