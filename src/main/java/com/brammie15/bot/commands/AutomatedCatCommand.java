package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AutomatedCatCommand extends Command {


    @Override
    public String getName() {
        return "!signup";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Signing up sexy").queue();
        switch (UtilCommands.addToRecievers(event.getAuthor())){
            case ALREADY_EXISTS -> {
                event.getChannel().sendMessage("Ur already on my hit list").queue();
            }
            case FAILED -> {
                event.getChannel().sendMessage("the Hot owner of this bot broke something").queue();
            }
            case SUCCES -> {
                event.getChannel().sendMessage("Done sexy").queue();

            }
        }

    }

    static class SuperkoolSex implements Runnable {
        long channelID;



        @Override
        public void run() {

        }
    }
}


