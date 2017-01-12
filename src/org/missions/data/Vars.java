package org.missions.data;

import org.missions.data.enums.MiningLocation;
import viking.api.skills.mining.enums.RockType;

/**
 * Created by Sphiinx on 1/11/2017.
 */
public class Vars {

    public static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = null;
    }

    public boolean is_upgrading_pickaxe, needsBronzePickAxe;

    public MiningLocation mining_location = MiningLocation.W_VARROCK_WEST_1;
    public RockType rock_type = RockType.TIN_ORE;

}

