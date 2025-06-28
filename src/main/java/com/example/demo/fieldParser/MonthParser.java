package com.example.demo.fieldParser;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.util.AppUtil;

import java.util.List;

public class MonthParser implements CronFieldParser {
    private static final CronField fieldType = CronField.MONTH;


    @Override
    public List<Integer> parse(String field) throws InvalidCronFieldException {
        return AppUtil.parseField(field, fieldType);
    }
}
