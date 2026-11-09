package com.codecool.brewery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BreweryTest {

    private Brewery brewery;
    private Brewer masterBrewer;
    private Brewer helper1;
    private Brewer helper2;
    private Brewer helper3;

    @BeforeEach
    void setUp() {
        brewery = new Brewery("Test Brewery");

        // Set up brewers
        // TODO: master brewer: "Master John", 15 years experience
        // TODO: helper brewer: "Helper Alice", 3 years experience, available Mon, Tue, Wed
        // TODO: helper brewer: "Helper Bob", 2 years experience, available Mon, Thu, Fri
        // TODO: helper brewer: "Helper Charlie", 4 years experience, available Tue, Wed, Thu

        // TODO: add master and helpers to brewery
    }

    @Test
    @DisplayName("Simple Test: Production capacity on single day equals available helpers")
    void testProductionCapacityOnSingleDay() {
        // Monday: helper1 and helper2 are available (2 helpers)
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY));
        assertEquals(2, capacity, "Production capacity should equal number of helpers on Monday");
    }

    @Test
    @DisplayName("Production capacity on multiple days returns sum of helpers")
    void testProductionCapacityOnMultipleDays() {
        // TODO: implement test for multiple days
    }

    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {
        // TODO: implement test for invalid day name
    }

    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        // Add fresh beer
        // TODO: create and add beer: "Fresh", 5.0%, produced 100 days ago

        // Add matured beers
        // TODO: create and add beer: "Old Ale", 6.0%, produced 200 days ago
        // TODO: create and add beer: "Old Lager", 5.5%, produced 190 days ago

        List<String> expired = brewery.findMaturedBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }

    @Test
    @DisplayName("Find optimal production days returns days with most helpers")
    void testFindOptimalProductionDays() {
        // TODO: implement test for finding optimal production days for the given setup
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {
        // TODO: implement test for fermentation days
        // TODO: create Lager: "Test Lager", 5.0%, today; fermentation time shall be 21 days
        // TODO: create Ale: "Test Ale", 5.5%, today; fermentation time shall be 14 days
    }

    @Test
    @DisplayName("Beer type has correct storage temperature")
    void testBeerStorageTemperature() {
        // TODO: implement test for storage temperature
        // TODO: create Lager: "Test Lager", 5.0%, today
        // TODO: create Ale: "Test Ale", 5.5%, today
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        // TODO: implement test for helper brewer availability
    }

}
