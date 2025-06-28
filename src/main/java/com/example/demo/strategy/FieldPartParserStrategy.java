package com.example.demo.strategy;

import com.example.demo.constants.CronField;
import com.example.demo.exception.InvalidCronFieldException;

import java.util.List;

public interface FieldPartParserStrategy {

    boolean supports(String part);

    void validate(String expression, CronField fieldType) throws InvalidCronFieldException;

    List<Integer> parse(String part, CronField fieldType) throws InvalidCronFieldException;
}
