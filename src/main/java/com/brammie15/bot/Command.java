package com.brammie15.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

    public abstract String getName();

    public abstract void run(MessageReceivedEvent event);

}
