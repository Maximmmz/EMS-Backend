package com.hw.demo.enums;

public enum Level {
    EMPLOYEE(1), LOWER_MANAGEMENT(2), UPPER_MANAGEMENT(3), EXECUTIVE(4);

    private final int value;
    Level(int value){ this.value=value; }
    public int getValue(){ return value; }

    public static Level fromInt(int v) {
        for (Level l : values()) if (l.value == v) return l;
        throw new IllegalArgumentException("Invalid level");
    }
}
