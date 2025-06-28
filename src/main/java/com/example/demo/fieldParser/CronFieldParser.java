package com.example.demo.fieldParser;

import com.example.demo.exception.InvalidCronFieldException;

import java.util.List;

public interface CronFieldParser {
    List<Integer> parse(String field) throws InvalidCronFieldException;
}
