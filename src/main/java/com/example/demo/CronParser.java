package com.example.demo;

import com.example.demo.util.AppUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CronParser {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please provide a cron expression.");
            return;
        }

        String input = String.join(" ", args);

        CronExpression cronExpression = new CronExpression(input);
        AppUtil.print(cronExpression);
    }

}


