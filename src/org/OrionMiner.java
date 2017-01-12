package org;

import viking.framework.goal.GoalList;
import viking.framework.mission.Mission;
import viking.framework.script.VikingScript;

public class OrionMiner extends Mission
{

	public OrionMiner(VikingScript script)
	{
		super(script);
	}

	@Override
	public boolean canEnd()
	{
		return false;
	}

	@Override
	public String getMissionName()
	{
		return null;
	}

	@Override
	public String getCurrentTaskName()
	{
		return null;
	}

	@Override
	public String getEndMessage()
	{
		return null;
	}

	@Override
	public GoalList getGoals()
	{
		return null;
	}

	@Override
	public String[] getMissionPaint()
	{
		return null;
	}

	@Override
	public int execute()
	{
		return 0;
	}

	@Override
	public void onMissionStart()
	{
	}

	@Override
	public void resetPaint()
	{
	}

}
