package com.example.demo;

import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.fieldParser.CronFieldParser;
import lombok.Getter;

import java.util.*;

import static com.example.demo.constants.ApplicationConstant.SUPPORTED_FIELDS;

@Getter
public class CronExpression {

    private final Map<String, List<Integer>> fieldMap = new LinkedHashMap<>();

    private final String command;

    public CronExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Cron expression cannot be null or empty");
        }
        String[] tokens = expression.trim().split("\\s+");


        if (tokens.length < SUPPORTED_FIELDS.size()) {
            throw new IllegalArgumentException("Invalid cron expression: must have " + SUPPORTED_FIELDS.size() + " fields + command");
        }


        parseFields(tokens);

        this.command = extractCommand(tokens);
    }

    private void parseFields(String[] tokens) {
        for (int i = 0; i < SUPPORTED_FIELDS.size(); i++) {
            String fieldName = SUPPORTED_FIELDS.get(i);
            String fieldValue = tokens[i];


            CronFieldParser parser = CronFieldParserFactory.getParser(fieldName);

            try {
                List<Integer> parsedValues = parser.parse(fieldValue);
                fieldMap.put(fieldName, parsedValues);
            } catch (InvalidCronFieldException e) {
                throw new IllegalArgumentException("Failed to parse field '" + fieldName + "' with value '" + fieldValue + "': " + e.getMessage());
            }
        }
    }

    private String extractCommand(String[] tokens) {
        return String.join(" ", Arrays.copyOfRange(tokens, SUPPORTED_FIELDS.size(), tokens.length));
    }

}