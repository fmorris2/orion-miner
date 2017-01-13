package org.missions.tasks.pickaxe;

import org.missions.OrionMiner;
import org.missions.data.OM_Vars;
import org.osbot.rs07.api.map.Position;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class GetBronzePickaxe extends Task<OrionMiner> {
    private static final Position TUTOR_POS = new Position(3228, 3245, 0);
    private static final int DIST_THRESH = 7;

    public GetBronzePickaxe(OrionMiner mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        return OM_Vars.get().needsBronzePickAxe;
    }

    @Override
    public void execute() {
        script.log(this, false, "Get Bronze Pickaxe");
        if (myPosition().distance(TUTOR_POS) > DIST_THRESH)
            walkUtils.walkTo(TUTOR_POS);
        else if (iFact.dialogue("Talk-to", "Mining tutor", 15, 1).execute() && inventory.contains("Bronze pickaxe"))
            OM_Vars.get().needsBronzePickAxe = false;

    }
}


