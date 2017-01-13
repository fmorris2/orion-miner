package org.missions.tasks;

import org.missions.OrionMiner;
import org.missions.data.OM_Vars;
import org.osbot.rs07.api.def.ObjectDefinition;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.model.RS2Object;
import viking.api.Timing;
import viking.api.filter.VFilters;
import viking.framework.task.Task;

import java.util.Arrays;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class WalkToMiningLocation extends Task<OrionMiner> {


    public WalkToMiningLocation(OrionMiner mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        if (OM_Vars.get().is_upgrading_pickaxe)
            return false;

        final RS2Object ROCK = getValidRock();
        return ROCK == null && !inventory.isFull() && mining.getBestUsablePickaxe(false) != null;
    }

    @Override
    public void execute() {
        if (walkUtils.walkToArea(OM_Vars.get().mining_location.getArea(), () -> {
            final RS2Object ROCK = getValidRock();
            return ROCK != null && ROCK.isVisible();
        })) {
            Timing.waitCondition(() -> OM_Vars.get().mining_location.getArea().contains(myPlayer()), 150, random(2000, 2500));
        }
    }

    private RS2Object getValidRock() {
        Filter filter = VFilters.and(new NameFilter<>("Rocks"), colorFilter((OM_Vars.get().rock_type.getRockColor())));

        return objects.closest(filter);
    }

    private Filter<RS2Object> colorFilter(short rock_color) {
        return rs2Object -> {
            if (rs2Object == null)
                return false;

            if (!OM_Vars.get().mining_location.getArea().contains(rs2Object))
                return false;

            final ObjectDefinition object_definition = rs2Object.getDefinition();
            if (object_definition == null)
                return false;

            final short[] object_colors = object_definition.getModifiedModelColors();
            if (object_colors == null)
                return false;

            for (short color : object_colors)
                return color == rock_color;

            return false;
        };
    }

    @Override
    public String toString() {
        return "Walking to mining location";
    }
}

