package org.missions.data;

import org.missions.data.enums.MiningLocation;
import viking.api.skills.mining.enums.RockType;

/**
 * Created by Sphiinx on 1/11/2017.
 */
public class OM_Vars {

    public static OM_Vars vars;

    public static OM_Vars get() {
        return vars == null ? vars = new OM_Vars() : vars;
    }

    public static void reset() {
        vars = null;
    }

    public boolean is_upgrading_pickaxe;
    public boolean needs_bronze_pickaxe;

    public MiningLocation mining_location = MiningLocation.M_LUMBRIDGE_SOUTH_1;
    public RockType rock_type = RockType.TIN_ORE;

}

