package org.missions.tasks;

import org.missions.OrionMiner;
import org.missions.data.OM_Vars;
import org.osbot.rs07.api.def.ObjectDefinition;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.model.RS2Object;
import viking.api.Timing;
import viking.api.filter.VFilters;
import viking.api.skills.mining.enums.PickaxeType;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class MineRock extends Task<OrionMiner> {

    public MineRock(OrionMiner mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        if (OM_Vars.get().is_upgrading_pickaxe)
            return false;

        final RS2Object ROCK = getValidRock();
        return ROCK != null && !inventory.isFull() && (inventory.getAmount(PickaxeType.getItemIDs()) > 0 || equipment.getAmount(PickaxeType.getItemIDs()) > 0);
    }

    @Override
    public void execute() {
        final RS2Object ROCK = getValidRock();
        if (ROCK == null)
            return;

        if (myPlayer().getAnimation() != -1 || myPlayer().isMoving())
            return;

        if (ROCK.isVisible()) {
            if (ROCK.interact("Mine"))
                Timing.waitCondition(() -> myPlayer().getAnimation() != -1, 150, random(2000, 2500));
        } else {
            if (walkUtils.walkToArea(ROCK.getArea(3)))
                Timing.waitCondition(ROCK::isVisible, 150, random(2000, 2500));
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
        return "Mining " + OM_Vars.get().rock_type;
    }

}

