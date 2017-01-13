package org.missions;

import org.missions.data.OM_Vars;
import org.missions.data.enums.MiningLocation;
import org.missions.tasks.OM_DepositItems;
import org.missions.tasks.MineRock;
import org.missions.tasks.WalkToMiningLocation;
import org.missions.tasks.pickaxe.EquipPickaxe;
import org.missions.tasks.pickaxe.GetBronzePickaxe;
import org.missions.tasks.pickaxe.GetPickaxe;
import org.missions.tasks.pickaxe.UpgradePickaxe;
import org.osbot.rs07.api.ui.Message;
import viking.api.skills.mining.enums.RockType;
import viking.framework.command.CommandReceiver;
import viking.framework.goal.GoalList;
import viking.framework.goal.impl.InfiniteGoal;
import viking.framework.mission.Mission;
import viking.framework.script.VikingScript;
import viking.framework.task.TaskManager;

public class OrionMiner extends Mission implements CommandReceiver {

	private final TaskManager<OrionMiner> TASK_MANAGER = new TaskManager<>(this);

	private CommandReceiver orion_main;
	private RockType target;

	public OrionMiner(VikingScript script, RockType target_type) {
		super(script);
		orion_main = script instanceof CommandReceiver ? (CommandReceiver) script : null;
		target = target_type;
	}

	@Override
	public boolean canEnd() {
		return false;
	}

	@Override
	public String getMissionName() {
		return null;
	}

	@Override
	public String getCurrentTaskName() {
		return TASK_MANAGER.getStatus();
	}

	@Override
	public String getEndMessage() {
		return null;
	}

	@Override
	public GoalList getGoals() {
		return new GoalList(new InfiniteGoal());
	}

	@Override
	public String[] getMissionPaint() {
		return null;
	}

	@Override
	public int execute() {
		TASK_MANAGER.loop();
		return 150;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMissionStart() {
		/*updateTargetTree();
		updateChoppingLoc();*/
		TASK_MANAGER.addTask(new OM_DepositItems(this), new GetPickaxe(this), new UpgradePickaxe(this),
				new EquipPickaxe(this), new WalkToMiningLocation(this), new MineRock(this),
				new GetBronzePickaxe(this));
	}

	@Override
	public void resetPaint() {
	}

	private boolean updateTargetTree() {
		script.log(this, false, "Updating target rock");
		RockType old = OM_Vars.get().rock_type;
		OM_Vars.get().rock_type = mining.getBestMineableRockType(false);
		if (OM_Vars.get().rock_type.ordinal() > target.ordinal())
			OM_Vars.get().rock_type = target;

		return old != OM_Vars.get().rock_type;
	}

	public void updateChoppingLoc() {
		script.log(this, false, "Updating mining loc....");
		orion_main.receiveCommand("getLoc:wc:free:" + OM_Vars.get().rock_type);
	}

	@Override
	public void receiveCommand(String command) {
		script.log(this, false, "Received command: " + command);
		String[] parts = command.split(":");
		if (parts[0].equals("bestLoc")) {
			MiningLocation bestLoc = MiningLocation.valueOf(parts[1]);
			OM_Vars.get().mining_location = bestLoc;
			script.log(this, false, "New best location: " + bestLoc);
		}
	}

	@Override
	public void onMessage(Message m) {
		if (m.getMessage().contains("advanced a Woodcutting level") && updateTargetTree())
			updateChoppingLoc();
	}
}
