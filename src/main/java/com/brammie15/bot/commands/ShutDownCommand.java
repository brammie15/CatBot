package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShutDownCommand extends Command {


    @Override
    public String getName() {
        return "!shutdown";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Ok sexy").queue();
        System.exit(2);
    }

}
