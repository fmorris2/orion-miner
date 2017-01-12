package org.missions.tasks.pickaxe;

import org.missions.OrionMiner;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.Skill;
import viking.api.Timing;
import viking.api.skills.mining.enums.PickaxeType;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class EquipPickaxe extends Task<OrionMiner> {

    private PickaxeType best_usable_pickaxe;

    public EquipPickaxe(OrionMiner mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        best_usable_pickaxe = mining.getBestUsablePickaxe(false);
        return best_usable_pickaxe != null && !equipment.isWieldingWeapon(best_usable_pickaxe.getItemID()) && skills.getStatic(Skill.ATTACK) >= best_usable_pickaxe.getAttackLevel();
    }

    @Override
    public void execute() {
        if (bank.isOpen())
            if (bank.close())
                Timing.waitCondition(() -> !bank.isOpen(), 150, random(2000, 2500));

        final Item item_to_equip = inventory.getItem(PickaxeType.getItemIDs());
        if (item_to_equip == null)
            return;

        if (item_to_equip.interact("Wield"))
            Timing.waitCondition(() -> equipment.isWieldingWeapon(best_usable_pickaxe.getItemID()), 150, random(2000, 2500));
    }

    @Override
    public String toString() {
        return "Equipping axe";
    }

}

