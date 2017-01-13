package org.missions.tasks.pickaxe;

import org.missions.OrionMiner;
import org.missions.data.OM_Vars;
import viking.api.Timing;
import viking.api.skills.mining.enums.PickaxeType;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class UpgradePickaxe extends GetPickaxe {

    private PickaxeType appropriate_pickaxe;
    private PickaxeType current_pickaxe;

    public UpgradePickaxe(OrionMiner mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        if (!client.isLoggedIn() || OM_Vars.get().is_upgrading_pickaxe)
            return false;

        if (!bank.isOpen())
            return false;

        appropriate_pickaxe = mining.currentAppropriatePickaxe();
        current_pickaxe = mining.getBestUsablePickaxe(false);

        return current_pickaxe != null && current_pickaxe != appropriate_pickaxe && bank.getItem(appropriate_pickaxe.getItemID()) != null;

    }

    @Override
    public void execute() {
        OM_Vars.get().is_upgrading_pickaxe = true;

        super.execute();

        if (equipment.getItems().length > 0)
            if (bank.depositWornItems())
                Timing.waitCondition(() -> equipment.getItems().length <= 0, 150, random(2000, 2500));
    }

    @Override
    public String toString() {
        return "Upgrading pickaxe";
    }

}

