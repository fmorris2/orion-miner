package org.missions.data.enums;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import viking.api.skills.mining.enums.RockType;

/**
 * Created by Sphiinx on 1/11/2017.
 */
public enum MiningLocation {

    M_VARROCK_WEST_1(new Area(new Position[]{
            new Position(3178, 3381, 0),
            new Position(3188, 3380, 0),
            new Position(3182, 3362, 0),
            new Position(3169, 3365, 0)
    }), false, false, 20, RockType.CLAY, RockType.TIN_ORE, RockType.IRON_ORE),
    M_VARROCK_E_1(new Area(new Position[]{
            new Position(3277, 3373, 0),
            new Position(3295, 3373, 0),
            new Position(3295, 3353, 0),
            new Position(3277, 3353, 0)
    }), false, false, 20, RockType.TIN_ORE, RockType.COPPER_ORE, RockType.IRON_ORE),
    M_BARBARIAN_VILLAGE_1(new Area(new Position[]{
            new Position(3076, 3425, 0),
            new Position(3085, 3425, 0),
            new Position(3085, 3415, 0),
            new Position(3077, 3416, 0)
    }), false, false, 10, RockType.TIN_ORE, RockType.COAL),
    M_RIMMINGTON_1(new Area(new Position[]{
            new Position(2992, 3254, 0),
            new Position(2992, 3225, 0),
            new Position(2963, 3227, 0),
            new Position(2963, 3252, 0)
    }), false, false, 40, RockType.CLAY, RockType.TIN_ORE, RockType.COPPER_ORE, RockType.IRON_ORE, RockType.GOLD),
    M_LUMBRIDGE_SOUTH_1(new Area(new Position[]{
            new Position(3220, 3152, 0),
            new Position(3234, 3152, 0),
            new Position(3234, 3142, 0),
            new Position(3220, 3142, 0)
    }
    ), false, false, 20, RockType.COPPER_ORE, RockType.TIN_ORE),
    M_LUMBRIDGE_SOUTH_2(new Area(new Position[]{
            new Position(3152, 3156, 0),
            new Position(3152, 3141, 0),
            new Position(3140, 3141, 0),
            new Position(3142, 3156, 0)
    }), false, false, 20, RockType.COAL, RockType.MITHRIL, RockType.ADAMANTITE),
    M_AL_KHARID(new Area(new Position[]{
            new Position(3298, 3319, 0),
            new Position(3293, 3311, 0),
            new Position(3294, 3307, 0),
            new Position(3290, 3299, 0),
            new Position(3294, 3291, 0),
            new Position(3290, 3282, 0),
            new Position(3291, 3275, 0),
            new Position(3307, 3274, 0),
            new Position(3307, 3278, 0),
            new Position(3309, 3281, 0),
            new Position(3304, 3286, 0),
            new Position(3308, 3292, 0),
            new Position(3304, 3297, 0),
            new Position(3309, 3303, 0),
            new Position(3304, 3308, 0),
            new Position(3307, 3312, 0),
            new Position(3302, 3321, 0)
    }), false, false, 40, RockType.COPPER_ORE, RockType.TIN_ORE, RockType.SILVER_ORE, RockType.IRON_ORE, RockType.COAL, RockType.GOLD, RockType.MITHRIL, RockType.ADAMANTITE);


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

