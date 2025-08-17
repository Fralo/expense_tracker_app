package com.expensetracker.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import com.expensetracker.utils.converters.AmountConverter;
import com.expensetracker.utils.converters.AmountConverterFactory;
import com.expensetracker.utils.converters.DecimalAmountConverter;

public class AmountConverterFactoryTest {

    @Test
    public void testGetConverter_Decimal() {
        AmountConverter converter = AmountConverterFactory.getConverter("decimal");
        assertTrue(converter instanceof DecimalAmountConverter);
    }

    @Test
    public void testGetConverter_Default() {
        AmountConverter converter = AmountConverterFactory.getConverter("default");
        assertTrue(converter instanceof DecimalAmountConverter);
    }

    @Test
    public void testGetConverter_Unknown() {
        try {
            AmountConverterFactory.getConverter("unknown");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Unknown converter type: unknown", e.getMessage());
        }
    }

    @Test
    public void testGetDefaultConverter() {
        AmountConverter converter = AmountConverterFactory.getDefaultConverter();
        assertTrue(converter instanceof DecimalAmountConverter);
    }
}