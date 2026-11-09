package com.codecool.brewery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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

        masterBrewer = new MasterBrewer("Master John", 15);
        helper1 = new HelperBrewer("Helper Alice", 3, Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY));
        helper2 = new HelperBrewer("Helper Bob", 2, Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        helper3 = new HelperBrewer("Helper Charlie", 4, Set.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY));

        brewery.addBrewer(masterBrewer);
        brewery.addBrewer(helper1);
        brewery.addBrewer(helper2);
        brewery.addBrewer(helper3);
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
        int capacity = brewery.calculateProductionCapacity(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        assertEquals(4, capacity);
    }

    @Test
    @DisplayName("Production capacity throws exception on invalid day name")
    void testProductionCapacityWithInvalidDay() {
        assertThrows(IllegalArgumentException.class, () -> brewery.findOptimalProductionDays("FUNDAY", "MONDAY"));
        assertThrows(IllegalArgumentException.class, () -> brewery.findOptimalProductionDays("MONDAY", "INVALID_DAY"));
        assertThrows(NullPointerException.class, () -> brewery.findOptimalProductionDays(null, "MONDAY"));
    }

    @Test
    @DisplayName("Find expired beers returns beers older than 180 days")
    void testFindExpiredBeers() {
        Beer fresh = new Lager("Fresh", 5.0, LocalDate.now().minusDays(100));
        Beer oldAle = new Ale( "Old Ale", 6.0, LocalDate.now().minusDays(200));
        Beer oldLager = new Lager("Old Lager", 5.5, LocalDate.now().minusDays(190));

        brewery.addBeerToStock(fresh);
        brewery.addBeerToStock(oldAle);
        brewery.addBeerToStock(oldLager);

        List<String> expired = brewery.findMaturedBeers();

        assertEquals(2, expired.size());
        assertTrue(expired.contains("Old Ale"));
        assertTrue(expired.contains("Old Lager"));
        assertFalse(expired.contains("Fresh"));
    }

    @Test
    @DisplayName("Beer type has correct fermentation days")
    void testBeerFermentationDays() {
        LocalDate today = LocalDate.now();

        Beer testLager = new Lager("Test Lager", 5.0, today);
        assertEquals(21, testLager.getFermentationDays(), "Lager fermentation time must be 21 days.");
        assertEquals(today.plusDays(21), testLager.getReadyDate(), "Lager ready date must be 21 days after production.");

        Beer testAle = new Ale("Test Ale", 5.5, today);
        assertEquals(14, testAle.getFermentationDays(), "Ale fermentation time must be 14 days.");
        assertEquals(today.plusDays(14), testAle.getReadyDate(), "Ale ready date must be 14 days after production.");
    }

    @Test
    @DisplayName("Beer type has correct storage temperature")
    void testBeerStorageTemperature() {
        LocalDate today = LocalDate.now();

        Beer testLager = new Lager("Test Lager", 5.0, today);
        assertTrue(testLager.canBeStoredAt(4.0), "Lager must be safely stored at 4.0°C");
        assertTrue(testLager.canBeStoredAt(0.0), "Lager must be safely stored at 0.0°C");
        assertFalse(testLager.canBeStoredAt(5.0), "Lager must not be safely stored at 5.0°C");
        assertFalse(testLager.canBeStoredAt(10.0), "Lager must not be safely stored at 10.0°C");

        Beer testAle = new Ale("Test Ale", 5.5, today);
        assertTrue(testAle.canBeStoredAt(15.0), "Ale must be safely stored at 15.0°C");
        assertTrue(testAle.canBeStoredAt(20.0), "Ale must be safely stored at 20.0°C");
        assertFalse(testAle.canBeStoredAt(14.9), "Ale must not be safely stored at 14.9°C");
        assertFalse(testAle.canBeStoredAt(25.1), "Ale must not be safely stored at 25.1°C");
    }

    @Test
    @DisplayName("Helper brewer availability is correct")
    void testHelperBrewerAvailability() {
        assertTrue(helper1.isAvailableOn((DayOfWeek.MONDAY)));
        assertTrue(helper1.isAvailableOn((DayOfWeek.TUESDAY)));
        assertTrue(helper1.isAvailableOn((DayOfWeek.WEDNESDAY)));
        assertFalse(helper1.isAvailableOn((DayOfWeek.THURSDAY)));
        assertFalse(helper1.isAvailableOn((DayOfWeek.FRIDAY)));
        assertFalse(helper1.isAvailableOn((DayOfWeek.SATURDAY)));
        assertFalse(helper1.isAvailableOn((DayOfWeek.SUNDAY)));
    }
}
