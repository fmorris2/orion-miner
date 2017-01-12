package org.missions.tasks.pickaxe;

import org.missions.OrionMiner;
import org.missions.data.Vars;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.EquipmentSlot;
import viking.api.Timing;
import viking.api.skills.mining.enums.PickaxeType;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class GetPickaxe extends Task<OrionMiner> {

    public GetPickaxe(OrionMiner mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        if (!client.isLoggedIn() || !myPlayer().isVisible() || Vars.get().is_upgrading_pickaxe
                || Vars.get().needsBronzePickAxe)
            return false;

        return !equipment.isWearingItem(EquipmentSlot.WEAPON) && mining.getBestUsablePickaxe(false) == null;
    }

    @Override
    public void execute() {
        if (bank.isOpen()) {
            final Item[] INVENTORY_CACHE = inventory.getItems();
            if (!inventory.isEmpty())
                if (bank.depositAll())
                    Timing.waitCondition(() -> INVENTORY_CACHE.length != inventory.getItems().length, 150, random(2000, 2500));

            final PickaxeType best_usable_axe = mining.getBestUsablePickaxe(true);
            if (best_usable_axe == null) {
                Vars.get().needsBronzePickAxe = true;
                return;
            }

            if (bank.withdraw(best_usable_axe.getItemID(), 1))
                if (Timing.waitCondition(() -> inventory.getItems().length > 0, 150, random(2000, 2500)))
                    if (mining.getBestUsablePickaxe(false) == best_usable_axe)
                        Vars.get().is_upgrading_pickaxe = false;
        } else {
            if (bankUtils.isInBank()) {
                if (bankUtils.open())
                    Timing.waitCondition(() -> bank.isOpen(), 150, random(2000, 2500));
            } else {
                if (walkUtils.walkToArea(bankUtils.getClosest()))
                    Timing.waitCondition(() -> bankUtils.isInBank(), 150, random(2000, 2500));
            }
        }

    }

    @Override
    public String toString() {
        return "Getting pickaxe";
    }

}

