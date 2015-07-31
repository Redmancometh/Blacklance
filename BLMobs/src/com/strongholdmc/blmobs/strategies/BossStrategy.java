package com.strongholdmc.blmobs.strategies;

import org.bukkit.entity.LivingEntity;

import net.citizensnpcs.api.ai.AttackStrategy;

public class BossStrategy implements AttackStrategy
{

    @Override
    public boolean handle(LivingEntity arg0, LivingEntity arg1)
    {
	return false;
    }

}
