package com.codecool.brewery;

import java.time.LocalDate;

public class Ale extends Beer {
    private static final int FERMENTATION_DAYS = 14;
    private static final double MAX_STORAGE_TEMPERATURE = 25.0;
    private static final double MIN_STORAGE_TEMPERATURE = 15.0;

    public Ale(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getFermentationDays() {
        return FERMENTATION_DAYS;
    }

    @Override
    public boolean canBeStoredAt(double temperature) {
        return temperature <= MAX_STORAGE_TEMPERATURE && temperature >= MIN_STORAGE_TEMPERATURE;
    }
}
