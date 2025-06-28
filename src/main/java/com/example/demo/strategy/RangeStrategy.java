package com.example.demo.strategy;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class RangeStrategy implements FieldPartParserStrategy {

    @Override
    public boolean supports(String part) {
        return part.contains("-") && !part.contains("/");
    }

    @Override
    public void validate(String expression, CronField fieldType) throws InvalidCronFieldException {
        String[] parts = expression.split("-");
        int start = AppUtil.parseValue(parts[0], fieldType);
        int end = AppUtil.parseValue(parts[1], fieldType);

        if (start > end) throw new InvalidCronFieldException("Range start is greater than end: " + expression);

        if (start < fieldType.min || end > fieldType.max)
            throw new InvalidCronFieldException("Range " + expression + " out of bounds (" + fieldType.min + "-" + fieldType.max + ")");
    }

    @Override
    //1-5
    public List<Integer> parse(String part, CronField fieldType) throws InvalidCronFieldException {
        String[] bounds = part.split("-");
        int start = AppUtil.parseValue(bounds[0], fieldType);
        int end = AppUtil.parseValue(bounds[1], fieldType);

        List<Integer> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(i);
        }
        return result;
    }
}
