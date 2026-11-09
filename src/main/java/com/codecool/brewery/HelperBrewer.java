package com.codecool.brewery;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

public class HelperBrewer extends Brewer{
    private final Set<DayOfWeek> workingDays;

    public HelperBrewer(String name, int yearsOfExperience, Collection<DayOfWeek> workingDays) {
        super(name, yearsOfExperience);
        if (workingDays == null || workingDays.isEmpty()) {
            this.workingDays = EnumSet.noneOf(DayOfWeek.class);
        } else {
            this.workingDays = EnumSet.copyOf(workingDays);
        }
    }

    public HelperBrewer(String name, int yearsOfExperience, DayOfWeek... workingDays) {
        super(name, yearsOfExperience);
        if (workingDays == null || workingDays.length == 0) {
            this.workingDays = EnumSet.noneOf(DayOfWeek.class);
        } else {
            this.workingDays = EnumSet.copyOf(Set.of(workingDays));
        }
    }

    public Set<DayOfWeek> getWorkingDays() {
        return Set.copyOf(workingDays);
    }

    @Override
    public boolean isAvailableOn(DayOfWeek day) {
        if (day == null) {
            return false;
        }
        return workingDays.contains(day);
    }

    @Override
    public BrewerRole getRole() {
        return BrewerRole.HELPER_BREWER;
    }
}
