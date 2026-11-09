package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Objects;

public class MasterBrewer extends Brewer {
    public MasterBrewer(String name, int yearsOfExperience) {
        super(name, yearsOfExperience);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        Objects.requireNonNull(day, "Day cannot be null");
        return true;
    }

    @Override
    public BrewerRole getRole() {
        return BrewerRole.MASTER_BREWER;
    }

    public int calculateProductionCapacity(List<DayOfWeek> productionDays, List<HelperBrewer> helperBrewers) {
        if (productionDays == null || helperBrewers == null) {
            return 0;
        }

        int totalBatches = 0;
        for (DayOfWeek day : productionDays) {
            int helpersOnDay = (int) helperBrewers.stream()
                    .filter(helper -> helper.isAvailableOn(day))
                    .count();
            totalBatches += helpersOnDay;
        }
        return totalBatches;
    }
}
