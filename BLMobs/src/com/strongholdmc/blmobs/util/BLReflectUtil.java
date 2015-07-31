package com.strongholdmc.blmobs.util;
import java.lang.reflect.Field;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R1.util.UnsafeList;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;

public class BLReflectUtil
{
    public static PathfinderGoalSelector getGoalSelector(LivingEntity entity)
    {
	EntityLiving elEntity = ((CraftLivingEntity) entity).getHandle();
	if (elEntity instanceof EntityInsentient)
	{
	    try
	    {
		Field f = EntityInsentient.class.getDeclaredField("goalSelector");
		f.setAccessible(true);
		PathfinderGoalSelector goalSelector = (PathfinderGoalSelector) f.get(elEntity);
		if(f!=null&&goalSelector!=null)
		{
		    goalSelector.a(1, new PathfinderGoalFloat((EntityInsentient) elEntity));
		    goalSelector.a();
		    Field f2 = Entity.class.getDeclaredField("a");
		    f2.setAccessible(true);
		    System.out.println(f2);   
		    printUnsafeList(goalSelector);
		}
	    }
	    catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
	    {
		e.printStackTrace();
	    }
	}
	return null;
    }
    @SuppressWarnings("rawtypes")
    public static void printUnsafeList(PathfinderGoalSelector selector)
    {
	try
	{
	    Field listAccessor = PathfinderGoalSelector.class.getDeclaredField("b");
	    Field listAccessor2 = PathfinderGoalSelector.class.getDeclaredField("c");
	    listAccessor.setAccessible(true);
	    listAccessor2.setAccessible(true);
	    UnsafeList ul = (UnsafeList) listAccessor.get(selector);
	    UnsafeList ul2 = (UnsafeList) listAccessor2.get(selector);
	    for(Object v : ul)
	    {
		System.out.println(v);
	    }
	    for(Object v : ul2)
	    {
		System.out.println(v);
	    }
	}
	catch(Throwable t)
	{
	    t.printStackTrace();
	}
    }
}
