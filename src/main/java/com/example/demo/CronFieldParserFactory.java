package com.example.demo;

import com.example.demo.fieldParser.*;

public class CronFieldParserFactory {
    public static CronFieldParser getParser(String fieldName) {
        if(fieldName.equalsIgnoreCase("year")){
            return new YearParser();
        }

        return switch (fieldName) {
            case "minute" -> new MinuteParser();
            case "hour" -> new HourParser();
            case "day of month" -> new DayOfMonthParser();
            case "month" -> new MonthParser();
            case "day of week" -> new DayOfWeekParser();
            default -> throw new IllegalArgumentException("Invalid field: " + fieldName);
        };
    }
}
