package com.example.demo;

import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.fieldParser.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldParsersTest {

    @Test
    void testMinuteParser() throws InvalidCronFieldException {
        MinuteParser parser = new MinuteParser();
        assertEquals(List.of(0, 15, 30, 45), parser.parse("*/15"));
        assertEquals(List.of(5), parser.parse("5"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("60"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("-1"));
    }

    @Test
    void testHourParser() throws InvalidCronFieldException {
        HourParser parser = new HourParser();
        assertEquals(List.of(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22), parser.parse("*/2"));
        assertEquals(List.of(5), parser.parse("5"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("24"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("-1"));
    }

    @Test
    void testDayOfMonthParser() throws InvalidCronFieldException {
        DayOfMonthParser parser = new DayOfMonthParser();
        assertEquals(List.of(1, 15), parser.parse("1,15"));
        assertEquals(List.of(10, 11, 12), parser.parse("10-12"));
        assertEquals(List.of(2, 4, 6, 8, 10), parser.parse("2-11/2"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("0"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("32"));
    }

    @Test
    void testMonthParser() throws InvalidCronFieldException {
        MonthParser parser = new MonthParser();
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), parser.parse("*"));
        assertEquals(List.of(6), parser.parse("6"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("0"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("13"));
    }

    @Test
    void testDayOfWeekParser() throws InvalidCronFieldException {
        DayOfWeekParser parser = new DayOfWeekParser();
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6), parser.parse("*"));
        assertEquals(List.of(1, 3, 5), parser.parse("1,3,5"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("-1"));
        assertThrows(InvalidCronFieldException.class, () -> parser.parse("7"));
    }
}
