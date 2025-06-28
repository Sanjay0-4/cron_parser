package com.example.demo.util;

import com.example.demo.CronExpression;
import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.strategy.FieldPartParserStrategy;
import com.example.demo.strategy.StrategyRegistry;
import lombok.experimental.UtilityClass;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class AppUtil {


    public static void print(CronExpression cron) {
        cron.getFieldMap().forEach((field, values) -> {
            System.out.printf("%-14s%s%n", field, join(values));
        });
        System.out.printf("%-14s%s%n", "command", cron.getCommand());
    }

    private static String join(List<Integer> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining(" "));
    }

    public static List<Integer> parseField(String field, CronField fieldType) throws InvalidCronFieldException {
        Set<Integer> values = new TreeSet<>();

        for (String part : field.split(",")) {
            FieldPartParserStrategy strategy = StrategyRegistry.getMatchingStrategy(part);
            strategy.validate(part, fieldType);
            values.addAll(strategy.parse(part, fieldType));
        }

        return new ArrayList<>(values);
    }

    public static int parseValue(String input, CronField fieldType) throws InvalidCronFieldException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidCronFieldException(String.format("Invalid number '%s' for field %s", input, fieldType.name()));
        }
    }
}
