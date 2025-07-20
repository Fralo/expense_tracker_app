package com.expensetracker.converters;

public class AmountConverterFactory {

    public static AmountConverter getConverter(String type) {
        switch (type.toLowerCase()) {
            case "decimal":
            case "default":
                return new DecimalAmountConverter();
            default:
                throw new IllegalArgumentException("Unknown converter type: " + type);
        }
    }

    public static AmountConverter getDefaultConverter() {
        return new DecimalAmountConverter();
    }
}