package org.missions.tasks;

import org.missions.OrionMiner;
import org.missions.data.OM_Vars;
import org.osbot.rs07.api.def.ObjectDefinition;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.filter.PositionFilter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import viking.api.Timing;
import viking.api.condition.LCondition;
import viking.api.filter.VFilters;
import viking.api.skills.mining.enums.PickaxeType;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class MineRock extends Task<OrionMiner> {

    private Position current_rock_position;

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
        if (isMiningEmptyRock() || myPlayer().getAnimation() == -1) {
            final RS2Object ROCK = getValidRock();
            if (ROCK == null)
                return;

            current_rock_position = ROCK.getPosition();

            if (ROCK.isVisible()) {
                if (ROCK.interact("Mine"))
                    Timing.waitCondition(() -> myPlayer().getAnimation() != -1, 150, random(4000, 5000));
            } else {
                if (walkUtils.walkToArea(ROCK.getArea(3), () -> {
                    final RS2Object rock_spot = getValidRock();
                    return rock_spot != null && rock_spot.isVisible();
                })) {
                    Timing.waitCondition(() -> getValidRock() != null, 150, random(2000, 2500));
                }
            }
        }
    }

    private boolean isMiningEmptyRock() {
        if (current_rock_position == null)
            return false;

        final Filter filter = VFilters.and(new PositionFilter<>(current_rock_position), new NameFilter<>("Rocks"));
        final RS2Object potential_empty_rock = objects.closest(filter);
        if (potential_empty_rock == null)
            return false;

        final ObjectDefinition object_definition = potential_empty_rock.getDefinition();
        if (object_definition == null)
            return false;

        final short[] object_colors = object_definition.getModifiedModelColors();
        return object_colors == null && myPlayer().isAnimating();
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

