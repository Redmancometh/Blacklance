package com.strongholdmc.blmobs.trait;

import net.citizensnpcs.api.ai.Goal;

public class blmBoss extends blm
{
    public void addGoal(int priority, Goal goal)
    {
	getNPC().getDefaultGoalController().addGoal(goal, priority);
    }
}
