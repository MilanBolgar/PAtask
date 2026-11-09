package com.codecool.brewery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Beer {
    private final String name;
    private final double alcoholContent;
    private final LocalDate productionDate;

    public Beer(String name, double alcoholContent, LocalDate productionDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (alcoholContent < 0.0 || alcoholContent > 100.0) {
            throw new IllegalArgumentException("Alcohol content must be between 0.0 and 100.0");
        }
        if (productionDate == null) {
            throw new IllegalArgumentException("Production date cannot be null");
        }

        this.name = name;
        this.alcoholContent = alcoholContent;
        this.productionDate = productionDate;
    }

    public String getName() {
        return name;
    }

    public double getAlcoholContent() {
        return alcoholContent;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public abstract int getFermentationDays();

    public abstract boolean canBeStoredAt(double temperature);

    public LocalDate getReadyDate() {
        return productionDate.plusDays(getFermentationDays());
    }

    public long getDaysUntilReady() {
        LocalDate readyDate = getReadyDate();
        LocalDate today = LocalDate.now();
        long daysRemaining = ChronoUnit.DAYS.between(today, readyDate);
        return Math.max(0, daysRemaining);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Beer beer)) return false;
        return Double.compare(beer.alcoholContent, alcoholContent) == 0 &&
                Objects.equals(name, beer.name) &&
                Objects.equals(productionDate, beer.productionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alcoholContent, productionDate);
    }

    @Override
    public String toString() {
        return "%s (alcohol content: %.1f%%, production date: %s, ready date: %s)".formatted(name, alcoholContent, productionDate, getReadyDate());
    }
}
