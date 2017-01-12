package org.missions.tasks;

import org.missions.OrionMiner;
import org.missions.data.Vars;
import viking.api.Timing;
import viking.api.skills.mining.enums.PickaxeType;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/12/2017.
 */
public class DepositItems extends Task<OrionMiner> {

    public DepositItems(OrionMiner mission) {
        super(mission);
    }

    public boolean validate() {
        return inventory.isFull();
    }

    public void execute() {
        if (Vars.get().mining_location.shouldUseDepositBox()) {
            if (depositBox.isOpen()) {
                if (depositBox.depositAllExcept(PickaxeType.getItemIDs()))
                    Timing.waitCondition(() -> !inventory.isFull(), 150, random(2000, 2500));
            } else {
                if (bankUtils.isInBank()) {
                    if (depositBox.open())
                        Timing.waitCondition(() -> depositBox.isOpen(), 150, random(2000, 2500));
                } else {
                    if (getWalking().webWalk(bankUtils.getAllBanks(false, true)))
                        Timing.waitCondition(() -> bankUtils.isInBank(), 150, random(2000, 2500));
                }
            }
        } else {
            if (bank.isOpen()) {
                if (bank.depositAllExcept(PickaxeType.getItemIDs()))
                    Timing.waitCondition(() -> !inventory.isFull(), 150, random(2000, 2500));
            } else {
                if (bankUtils.isInBank()) {
                    if (bankUtils.open())
                        Timing.waitCondition(conditions.BANK_OPEN, 150, random(2000, 2500));
                } else {
                    if (getWalking().webWalk(bankUtils.getAllBanks(false, false)))
                        Timing.waitCondition(() -> bankUtils.isInBank(), 150, random(2000, 2500));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Depositing items";
    }

}

