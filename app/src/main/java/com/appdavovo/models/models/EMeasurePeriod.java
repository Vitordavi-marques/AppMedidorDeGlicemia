package com.appdavovo.models.models;

public enum EMeasurePeriod {
    FASTING(1, "Jejum"),
    LUNCH_TIME(2, "Almoço"),
    DINNER_TIME(3, "Janta");

    private int enumId;
    private String name;

    private EMeasurePeriod(int enumId, String name) {
        this.enumId = enumId;
        this.name = name;
    }

    public int getEnumId() {
        return enumId;
    }

    public String getName() { return name; }

    public static EMeasurePeriod getByEnumId(int enumId) {
        switch (enumId) {
            case 1:
                return FASTING;
            case 2:
                return LUNCH_TIME;
            case 3:
                return DINNER_TIME;
            default:
                return null;
        }
    }
}
