package com.strongholdmc.blcore.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;

public enum RPGStatFinder
{
    STRENGTH(Pattern.compile("Strength: \\d+")), AGILITY(Pattern.compile("Agility: \\d+")), 
    INTELLECT(Pattern.compile("Intellect: \\d+")), VITALITY(Pattern.compile("Vitality: \\d+")),
    HIGH_DAMAGE(Pattern.compile("-\\d+")), LOW_DAMAGE(Pattern.compile("\\d+-")), DEFENSE(Pattern.compile("Defense: \\d+"));
    Pair<RPGStat, Pattern> pair;
    Pattern numberFinder = Pattern.compile("\\d+");
    RPGStatFinder(Pattern p)
    {
	this.pair=new Pair<RPGStat, Pattern>(RPGStat.getByName(this.name()), p);
    }
    public RPGStat getStatType()
    {
	return RPGStat.getByName(this.name());
    }
    
    public Pattern getPattern()
    {
	return pair.getValue();
    }
    
    public int tryGetValue(String s)
    {
	Matcher m = getPattern().matcher(s);
	if(m.find())
	{
	    String numString = s.substring(m.start(), m.end());
	    Matcher numMatch = numberFinder.matcher(numString);
	    if(numMatch.find())
	    {
		return Integer.parseInt(numString.substring(numMatch.start(), numMatch.end()));
	    }
	}
	return 0;
    }
}
