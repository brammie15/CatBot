package com.brammie15.bot;

import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.Map;

public abstract class AdvancedCommand extends Command{

    public abstract Map<Long, List<String>> getSelectMenuIds();

    public abstract void SelectMenuRun(SelectMenuInteractionEvent event);

}
