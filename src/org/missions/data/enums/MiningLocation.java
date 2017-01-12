package org.missions.data.enums;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import viking.api.skills.mining.enums.RockType;

/**
 * Created by Sphiinx on 1/11/2017.
 */
public enum MiningLocation {

    W_VARROCK_WEST_1(new Area(new Position[]{
            new Position(3178, 3381, 0),
            new Position(3188, 3380, 0),
            new Position(3182, 3362, 0),
            new Position(3169, 3365, 0)
    }), false, false, 20, RockType.CLAY, RockType.TIN_ORE, RockType.IRON_ORE);


    private final Area AREA;
    private final boolean MEMBERS;
    private final boolean SHOULD_USE_DEPOSIT_BOX;
    private final int CAPACITY;
    private final RockType[] ROCK_TYPE;

    MiningLocation(Area area, boolean members, boolean should_use_deposit_box, int capacity, RockType... rock_type) {
        this.AREA = area;
        this.MEMBERS = members;
        this.SHOULD_USE_DEPOSIT_BOX = should_use_deposit_box;
        this.CAPACITY = capacity;
        this.ROCK_TYPE = rock_type;
    }

    public Area getArea() {
        return AREA;
    }

    public boolean isMembers() {
        return MEMBERS;
    }

    public boolean shouldUseDepositBox() {
        return SHOULD_USE_DEPOSIT_BOX;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public RockType[] getRockTypes() {
        return ROCK_TYPE;
    }

    public boolean containsRockType(RockType t) {
        for (RockType type : ROCK_TYPE)
            if (type == t)
                return true;

        return false;
    }
}

