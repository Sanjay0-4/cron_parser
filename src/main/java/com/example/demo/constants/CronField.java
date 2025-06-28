package com.example.demo.constants;

public enum CronField {

    MINUTE(0, 59),
    HOUR(0, 23),
    DAY_OF_MONTH(1, 31),
    MONTH(1, 12),
    DAY_OF_WEEK(0, 6),
    YEAR(1970,2030);

    public final int min;
    public final int max;

    CronField(int min, int max) {
        this.min = min;
        this.max = max;
    }
}
