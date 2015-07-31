package com.simpsonwil.strongquests;

import java.util.ArrayList;

/**
 * Created by Wil on 1/22/2015.
 */
public class Quest
{
    //Ordered list of all quest parts
    private ArrayList<QuestPart> questParts;

    public Quest(ArrayList<QuestPart> questParts)
    {
        this.questParts = questParts;
    }

    //Get all the parts of the quests in order
    public ArrayList<QuestPart> getQuestParts()
    {
        return questParts;
    }

    @Override
    public Quest clone()
    {
        return new Quest(questParts);
    }
}
