package com.strongholdmc.blresources.util;
import org.bukkit.block.Block;
public class ResourceNode
{
    private Block b;
    private boolean isSet;
    public ResourceNode(Block b, boolean isSet)
    {
	this.b=b;
	this.isSet=isSet;
    }
    public void setNode()
    {
	this.isSet=true;
    }
    public boolean isSet()
    {
	return this.isSet;
    }
    public Block getBlock()
    {
	return b;
    }
}
