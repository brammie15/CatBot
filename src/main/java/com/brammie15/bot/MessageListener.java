package com.brammie15.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class MessageListener {

    public abstract String[] getList();

    public abstract void run(MessageReceivedEvent event);
}
