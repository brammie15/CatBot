package com.brammie15.bot;

import com.brammie15.bot.commands.CatCommand;
import com.brammie15.bot.commands.PingCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class CommandSex extends ListenerAdapter {
    public static final Set<Command> COMMANDS = new HashSet<>();

    public CommandSex() {
        COMMANDS.add(new PingCommand());
        COMMANDS.add(new CatCommand());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getAuthor().isBot()) return;
        StoreRecievedDm(event);
        System.out.println("(" + event.getMessage().getTimeCreated() + ") " + event.getAuthor().getName() +": "+event.getMessage().getContentDisplay());
        String content = event.getMessage().getContentRaw().trim().toLowerCase();
        COMMANDS.forEach(command -> {
            if(content.startsWith(command.getName().toLowerCase())) {
                command.run(event);
            }
        });
    }


    //Util commands
    private void StoreRecievedDm(MessageReceivedEvent event) {
        if(!(event.getChannelType() == ChannelType.PRIVATE)) {
            UtilCommands.storeToFile("chat", event.getAuthor(), event.getMessage().getContentRaw());
            return;
        }
        User user = event.getAuthor();
        UtilCommands.storeToFile(user.getName(), user, event.getMessage().getContentRaw());
    }

    private void storeSentDm(JDA jda, User user, String message) {
        UtilCommands.storeToFile(user.getName(), jda.getSelfUser(), message);
    }
}
