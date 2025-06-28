package com.example.demo.fieldParser;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;
import com.example.demo.util.AppUtil;

import java.util.List;

public class MinuteParser implements CronFieldParser {

    private static final CronField fieldType = CronField.MINUTE;

    @Override
    public List<Integer> parse(String field) throws InvalidCronFieldException {
        return AppUtil.parseField(field, fieldType);
    }

}
