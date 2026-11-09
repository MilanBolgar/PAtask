package com.codecool.brewery;

import java.time.LocalDate;

/**
 * Represents a beer produced by the brewery.
 * This is a basic implementation - you need to extend this to support different beer types.
 */
class Beer {
    String name;
    double alcoholContent;
    LocalDate productionDate;

    Beer(String name, double alcoholContent, LocalDate productionDate) {}

    // TODO: Add method to calculate days until beer is ready for sale
    // TODO: Consider how to handle different fermentation times for different beer types
    void getDaysUntilReady() {
    }

    // TODO: Add method to get storage temperature requirement
    // TODO: Consider canBeStoredAt method may accept parameters for temperature checks
    void canBeStoredAt() {
    }

    // TODO: Add method to get fermentation days
    void getFermentationDays() {
    }
}
