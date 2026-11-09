package com.codecool.brewery;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a brewery that manages beer production and staff.
 * Many methods are incomplete and need to be implemented.
 */
public class Brewery {
    private static final int MATURITY_THRESHOLD_DAYS = 180;

    private final String name;
    private final List<Brewer> brewers;
    private final Map<Beer, Integer> stock;

    public Brewery(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
        this.brewers = new ArrayList<>();
        this.stock = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public List<Brewer> getBrewers() {
        return Collections.unmodifiableList(brewers);
    }

    public void addBrewer(Brewer brewer) {
        Objects.requireNonNull(brewer, "Brewer cannot be null");

        if (brewer.getRole() == BrewerRole.MASTER_BREWER && getMasterBrewer().isPresent()) {
            throw new IllegalStateException("Only one Master Brewer can be assigned to the brewery at any time.");
        }

        brewers.add(brewer);
    }

    public Optional<MasterBrewer> getMasterBrewer() {
        return brewers.stream()
                .filter(b -> b.getRole() == BrewerRole.MASTER_BREWER)
                .map(b -> (MasterBrewer) b)
                .findFirst();
    }

    public List<HelperBrewer> getHelperBrewers() {
        return brewers.stream()
                .filter(b -> b.getRole() == BrewerRole.HELPER_BREWER)
                .map(b -> (HelperBrewer) b)
                .collect(Collectors.toList());
    }

    public void addBeerToStock(Beer beer) {
        addBeerToStock(beer, 1);
    }

    public void addBeerToStock(Beer beer, int quantity) {
        Objects.requireNonNull(beer, "Beer cannot be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        stock.merge(beer, quantity, Integer::sum);
    }

    /**
     * Retrieves a specific quantity of beer from stock.
     * TODO: Implement this method with proper error handling
     * - What happens if the beer doesn't exist?
     * - What happens if there's not enough quantity?
     * - Should this modify the stock?
     */
    public void retrieveBeerFromStock(String beerName, int quantity) {
        if (beerName == null || beerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Beer name cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        Beer targetBeer = stock.keySet().stream()
                .filter(beer -> getName().equalsIgnoreCase(beerName.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Beer not found in stock: " + beerName));

        int availableQuantity = stock.get(targetBeer);
        if (availableQuantity < quantity) {
            throw new IllegalArgumentException("Not enough stock for beer: " + beerName + " (Available: " + availableQuantity + ", Requested: " + quantity + ")");
        }

        if (availableQuantity == quantity) {
            stock.remove(targetBeer);
        } else {
            stock.put(targetBeer, availableQuantity - quantity);
        }
    }

    /**
     * Calculates the total production capacity across specific days.
     * Each day is independent - beer can be produced in a single day.
     * TODO: Implement this method
     * - Find the master brewer
     * - Count available helpers on each specified day
     * - Return the sum of helper counts across all days
     */
    public int calculateProductionCapacity(List<DayOfWeek> days) {
        if (days == null || days.isEmpty()) {
            return 0;
        }

        Optional<MasterBrewer> master = getMasterBrewer();
        if (master.isEmpty()) {
            return 0;
        }

        List<HelperBrewer> helpers = getHelperBrewers();
        return master.get().calculateProductionCapacity(days, helpers);
    }

    /**
     * Finds beers that are past their prime (older than 180 days).
     * TODO: Implement this method using Stream API
     * - Filter beers by age
     * - Return collection of beer names
     */
    public List<String> findMaturedBeers() {
        LocalDate today = LocalDate.now();
        return stock.keySet().stream()
                .filter(beer -> ChronoUnit.DAYS.between(beer.getProductionDate(), today) > MATURITY_THRESHOLD_DAYS)
                .map(Beer::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Finds the optimal days for production within a given range.
     * Returns days with maximum helper availability.
     */
    public List<String> findOptimalProductionDays(String startDayName, String endDayName) {
        DayOfWeek startDay = parseDayOfWeek(startDayName);
        DayOfWeek endDay = parseDayOfWeek(endDayName);

        // Get all days in range
        List<DayOfWeek> daysInRange = getDaysInRange(startDay, endDay);

        if (daysInRange.isEmpty()) {
            return Collections.emptyList();
        }

        // Map each day to its helper count
        Map<DayOfWeek, Integer> helperCountByDay = daysInRange.stream()
                .collect(Collectors.toMap(
                        day -> day,
                        this::countAvailableHelpers
                ));

        // Find maximum helper count
        int maxHelpers = helperCountByDay.values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        // Return days with maximum helper count
        return helperCountByDay.entrySet().stream()
                .filter(entry -> entry.getValue() == maxHelpers)
                .map(Map.Entry::getKey)
                .sorted()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to parse day names.
     */
    private DayOfWeek parseDayOfWeek(String dayName) {
        Objects.requireNonNull(dayName, "Day name cannot be null");
        try {
            return DayOfWeek.valueOf(dayName.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day name: " + dayName, e);
        }
    }

    /**
     * Helper method to get days in a range.
     */
    private List<DayOfWeek> getDaysInRange(DayOfWeek start, DayOfWeek end) {
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek current = start;

        // Handle wrap-around week
        while (true) {
            days.add(current);
            if (current == end) {
                break;
            }
            current = current.plus(1);
            // Prevent infinite loop
            if (days.size() > 7) {
                break;
            }
        }

        return days;
    }

    /**
     * Counts available helpers on a specific day.
     */
    private int countAvailableHelpers(DayOfWeek day) {
        if (day == null) {
            return 0;
        }
        return (int) brewers.stream()
                .filter(b -> b.getRole() == BrewerRole.HELPER_BREWER)
                .filter(b -> b.isAvailableOn(day))
                .count();
    }
}
