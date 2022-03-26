package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CatCommand extends Command {


    @Override
    public String getName() {
        return "cat";
    }

    @Override
    public void run(MessageReceivedEvent event) {
//        event.getChannel().sendMessage("This command will eventually be deprecated").queue();
        UtilCommands.sendCat(event);
    }

}
