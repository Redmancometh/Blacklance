package com.simpsonwil.strongquests;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Listener;

public abstract class AbstractQuest
{
    private List<Listener> stepListeners = new ArrayList<Listener>();
    public void addStepListener(Listener l)
    {
	stepListeners.add(l);
    }
}
