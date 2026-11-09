package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.Objects;

/**
 * Represents a brewer working at the brewery.
 * You need to extend this class to support different types of brewers.
 */
public abstract class Brewer {
    private final String name;
    private final int yearsOfExperience;

    public Brewer(String name, int yearsOfExperience) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative");
        }

        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getName() {
        return name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public abstract boolean isAvailableOn(DayOfWeek day);

    public boolean isAvailableOn(String dayName) {
        Objects.requireNonNull(dayName, "Day name cannot be null");
        return isAvailableOn(DayOfWeek.valueOf(dayName.trim().toUpperCase()));
    }

    public abstract BrewerRole getRole();

    @Override
    public int hashCode() {
        return Objects.hash(name, yearsOfExperience);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "name='" + name + '\'' + ", yearsOfExperience=" + yearsOfExperience + ", role=" + getRole() + '}';
    }
}
