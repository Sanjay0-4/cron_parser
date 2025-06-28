package com.example.demo;

import com.example.demo.util.AppUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.example.demo.constants.ApplicationConstant.SUPPORTED_FIELDS;
import static org.junit.jupiter.api.Assertions.*;

class CronExpressionTest {

    @Test
    void testValidCronExpression() {
        String expr = "*/15 23 1,15 1-3 1,2 /usr/bin/find";
        CronExpression cron = new CronExpression(expr);
        AppUtil.print(cron);

        Map<String, List<Integer>> fieldMap = cron.getFieldMap();
        assertEquals(SUPPORTED_FIELDS.size(), fieldMap.size());
        assertEquals(List.of(0, 15, 30, 45), fieldMap.get("minute"));
        assertEquals(List.of(23), fieldMap.get("hour"));
        assertEquals(List.of(1, 15), fieldMap.get("day of month"));
        assertEquals(List.of(1, 2, 3), fieldMap.get("month"));
        assertEquals(List.of(1, 2), fieldMap.get("day of week"));

        assertEquals("/usr/bin/find", cron.getCommand());
    }

    @Test
    void testNullExpression() {
        String expr = null;
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new CronExpression(expr));
        assertTrue(ex.getMessage().contains("Cron expression cannot be null"));
    }

    @Test
    void testInvalidTooFewFields() {
        String expr = "*/15 0 1,15 *";
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new CronExpression(expr));
        assertTrue(ex.getMessage().contains("Invalid cron expression"));
    }

    @Test
    void testInvalidFieldValue() {
        // Assuming "foo" is invalid for "minute"
        String expr = "foo 0 1,15 * 1-5 /bin/ls";
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new CronExpression(expr));
        assertTrue(ex.getMessage().contains("Invalid number"));
    }

    @Test
    void testCommandWithSpaces() {
        String expr = "0 0 1 1 0 /bin/echo Hello World";
        CronExpression cron = new CronExpression(expr);
        assertEquals("/bin/echo Hello World", cron.getCommand());
    }
}
