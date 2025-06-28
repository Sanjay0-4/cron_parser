package com.example.demo.strategy;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.util.AppUtil.parseValue;


public class StepStrategy implements FieldPartParserStrategy {

    @Override
    public boolean supports(String part) {
        return part.contains("/");
    }

    @Override
    public void validate(String expression, CronField fieldType) throws InvalidCronFieldException {
        String[] parts = expression.split("/");

        if (parts.length != 2)
            throw new InvalidCronFieldException("Invalid step expression: " + expression);

        String rangePart = parts[0];
        int step = AppUtil.parseValue(parts[1], fieldType);

        if (step <= 0)
            throw new InvalidCronFieldException("Step must be greater than 0 in: " + expression);

        if (rangePart.equals("*")) {
            return;
        }

        // Handle range with step: e.g., 1-10/2
        String[] range = rangePart.split("-");
        if (range.length != 2)
            throw new InvalidCronFieldException("Invalid range format in expression: " + expression);

        int start = AppUtil.parseValue(range[0], fieldType);
        int end = AppUtil.parseValue(range[1], fieldType);

        if (start > end)
            throw new InvalidCronFieldException("Start greater than end in range: " + expression);

        if (start < fieldType.min || end > fieldType.max)
            throw new InvalidCronFieldException("Range out of bounds: " + expression);
    }

    @Override
    //*/15 or 1-5/2
    public List<Integer> parse(String part, CronField fieldType) throws InvalidCronFieldException {
        String[] stepParts = part.split("/");
        String range = stepParts[0];
        int step = parseValue(stepParts[1], fieldType);

        int start = fieldType.min, end = fieldType.max;

        if (!range.equals("*")) {
            String[] bounds = range.split("-");
            start = parseValue(bounds[0],fieldType );
            end = parseValue(bounds[1], fieldType);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = start; i <= end; i += step) {
            result.add(i);
        }
        return result;
    }
}
