package com.example.demo.strategy;


import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;

import java.util.Collections;
import java.util.List;

import static com.example.demo.util.AppUtil.parseValue;

public class LiteralValueStrategy implements FieldPartParserStrategy {

    @Override
    public boolean supports(String part) {
        return !part.contains("-") && !part.contains("/") && !part.contains(",");
    }

    @Override
    public void validate(String expression, CronField fieldType) throws InvalidCronFieldException {
        String[] parts = expression.split(",");
        for (String part : parts) {
            int val = parseValue(part, fieldType);
            if (val < fieldType.min || val > fieldType.max) {
                throw new InvalidCronFieldException("Value " + val + " out of bounds (" + fieldType.min + "-" + fieldType.max + ") in expression: " + expression);
            }
        }
    }

    @Override
    //0, 15
    public List<Integer> parse(String part, CronField fieldType) throws InvalidCronFieldException {
        int value = parseValue(part, fieldType);
        return Collections.singletonList(value);
    }
}
