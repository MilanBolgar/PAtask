package com.codecool.brewery;

import java.time.LocalDate;

public class Lager extends Beer {
    private static final int FERMENTATION_DAYS = 21;
    private static final double MAX_STORAGE_TEMPERATURE = 5.0;

    public Lager(String name, double alcoholContent, LocalDate productionDate) {
        super(name, alcoholContent, productionDate);
    }

    @Override
    public int getFermentationDays() {
        return FERMENTATION_DAYS;
    }

    @Override
    public boolean canBeStoredAt(double temperature) {
        return temperature < MAX_STORAGE_TEMPERATURE;
    }
}
