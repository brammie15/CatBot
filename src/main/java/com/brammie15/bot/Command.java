package com.brammie15.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public abstract class Command {

    public abstract String getName();

    public abstract void run(MessageReceivedEvent event) throws IOException, InterruptedException;

}
