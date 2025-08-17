package com.expensetracker.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import com.expensetracker.utils.converters.DecimalAmountConverter;

public class DecimalAmountConverterTest {

    private DecimalAmountConverter converter;

    @Before
    public void setUp() {
        converter = new DecimalAmountConverter();
    }

    @Test
    public void testConvertToCents_ValidDecimal() {
        assertEquals(12345, converter.convertToCents("123.45"));
    }

    @Test
    public void testConvertToCents_ValidComma() {
        assertEquals(12345, converter.convertToCents("123,45"));
    }

    @Test
    public void testConvertToCents_NullAmount() {
        try {
            converter.convertToCents(null);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount cannot be null or empty", e.getMessage());
        }
    }

    @Test
    public void testConvertToCents_EmptyAmount() {
        try {
            converter.convertToCents(" ");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount cannot be null or empty", e.getMessage());
        }
    }

    @Test
    public void testConvertToCents_InvalidFormat() {
        try {
            converter.convertToCents("abc");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid amount format: abc", e.getMessage());
        }
    }

    @Test
    public void testConvertFromCents_Valid() {
        assertEquals("123.45", converter.convertFromCents(12345));
    }

    @Test
    public void testConvertFromCents_Negative() {
        try {
            converter.convertFromCents(-1);
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Cents cannot be negative", e.getMessage());
        }
    }
}