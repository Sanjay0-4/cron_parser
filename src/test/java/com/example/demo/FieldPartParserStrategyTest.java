package com.example.demo;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.strategy.LiteralValueStrategy;
import com.example.demo.strategy.RangeStrategy;
import com.example.demo.strategy.StepStrategy;
import com.example.demo.strategy.WildcardStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldPartParserStrategyTest {

    @Test
    void testRangeStrategy() throws InvalidCronFieldException {
        RangeStrategy strategy = new RangeStrategy();
        assertTrue(strategy.supports("1-5"));
        assertFalse(strategy.supports("*/5"));

        // Valid range
        strategy.validate("2-4", CronField.HOUR);
        assertEquals(List.of(2, 3, 4), strategy.parse("2-4", CronField.HOUR));

        // Invalid: start > end
        InvalidCronFieldException ex1 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("5-2", CronField.HOUR));
        assertTrue(ex1.getMessage().contains("Range start is greater than end"));

        // Invalid: out of bounds
        InvalidCronFieldException ex2 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("1-20", CronField.DAY_OF_WEEK));
        assertTrue(ex2.getMessage().contains("out of bounds"));
    }

    @Test
    void testLiteralValueStrategy() throws InvalidCronFieldException {
        LiteralValueStrategy strategy = new LiteralValueStrategy();
        assertTrue(strategy.supports("7"));
        assertFalse(strategy.supports("7-8"));

        // Valid value
        strategy.validate("5", CronField.DAY_OF_WEEK);
        assertEquals(List.of(5), strategy.parse("5", CronField.DAY_OF_WEEK));

        // Invalid: out of bounds
        InvalidCronFieldException ex = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("15", CronField.DAY_OF_WEEK));
        assertTrue(ex.getMessage().contains("out of bounds"));
    }

    @Test
    void testWildcardStrategy() throws InvalidCronFieldException {
        WildcardStrategy strategy = new WildcardStrategy();
        assertTrue(strategy.supports("*"));
        assertFalse(strategy.supports("*/5"));

        // Valid wildcard
        strategy.validate("*", CronField.MINUTE);
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6), strategy.parse("*", CronField.DAY_OF_WEEK));

        // Invalid: too many slashes
        InvalidCronFieldException ex1 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("*/2/3", CronField.MINUTE));
        assertTrue(ex1.getMessage().contains("Too many '/'"));

        // Invalid: step <= 0
        InvalidCronFieldException ex2 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("*/0", CronField.MINUTE));
        assertTrue(ex2.getMessage().contains("Invalid step value"));
    }

    @Test
    void testStepStrategy() throws InvalidCronFieldException {
        StepStrategy strategy = new StepStrategy();
        assertTrue(strategy.supports("*/2"));
        assertTrue(strategy.supports("1-5/2"));
        assertFalse(strategy.supports("1-5"));

        // Valid: */2
        strategy.validate("*/2", CronField.DAY_OF_WEEK);
        assertEquals(List.of(0, 2, 4, 6), strategy.parse("*/2", CronField.DAY_OF_WEEK));

        // Valid: 1-5/2
        strategy.validate("1-5/2", CronField.DAY_OF_WEEK);
        assertEquals(List.of(1, 3, 5), strategy.parse("1-5/2", CronField.DAY_OF_WEEK));

        // Invalid: step zero
        InvalidCronFieldException ex1 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("*/0", CronField.MINUTE));
        assertTrue(ex1.getMessage().contains("Step must be greater than 0"));

        // Invalid: bad format
        InvalidCronFieldException ex2 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("*/", CronField.DAY_OF_WEEK));
        assertTrue(ex2.getMessage().contains("Invalid step expression"));

        // Invalid: range format
        InvalidCronFieldException ex3 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("1-2-3/2", CronField.MINUTE));
        assertTrue(ex3.getMessage().contains("Invalid range format"));

        // Invalid: start > end
        InvalidCronFieldException ex4 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("5-1/2", CronField.DAY_OF_MONTH));
        assertTrue(ex4.getMessage().contains("Start greater than end"));

        // Invalid: out of bounds
        InvalidCronFieldException ex5 = assertThrows(InvalidCronFieldException.class,
                () -> strategy.validate("1-20/2", CronField.DAY_OF_WEEK));
        assertTrue(ex5.getMessage().contains("Range out of bounds"));
    }
}
