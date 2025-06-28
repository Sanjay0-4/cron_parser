package com.example.demo.strategy;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.AppUtil.parseValue;

public class WildcardStrategy implements FieldPartParserStrategy {

    @Override
    public boolean supports(String part) {
        return part.equals("*");
    }

    @Override
    public void validate(String expression, CronField fieldType) throws InvalidCronFieldException {
        String[] parts = expression.split("/");
        if (parts.length > 2)
            throw new InvalidCronFieldException("Too many '/' in wildcard expression: " + expression);

        if (parts.length == 2) {
            int step = parseValue(parts[1], fieldType);
            if (step <= 0)
                throw new InvalidCronFieldException("Invalid step value in wildcard expression: " + expression);
        }
    }

    @Override
    //*
    public List<Integer> parse(String part, CronField fieldType) {
        List<Integer> result = new ArrayList<>();
        for (int i = fieldType.min; i <= fieldType.max; i++) {
            result.add(i);
        }
        return result;
    }
}
