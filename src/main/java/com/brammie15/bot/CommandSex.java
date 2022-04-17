package com.brammie15.bot;

import com.brammie15.bot.commands.*;
import com.brammie15.bot.commands.Listners.SadListner;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CommandSex extends ListenerAdapter {
    public static final Set<Command> COMMANDS = new HashSet<>();
    public static final Set<MessageListener> LISTNERS = new HashSet<>();

    public CommandSex() {
        COMMANDS.add(new PingCommand());
        COMMANDS.add(new CatCommand());
//        COMMANDS.add(new SendCommand());
//        COMMANDS.add(new ShutDownCommand());
        COMMANDS.add(new CatBombCommand());
//        COMMANDS.add(new AutomatedCatCommand());
//        COMMANDS.add(new AnoyOwnerCommand());
//        COMMANDS.add(new AdvancedSendCommand());

        LISTNERS.add(new SadListner());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getAuthor().isBot()) return;
        StoreRecievedDm(event);
        System.out.println("(" + event.getMessage().getTimeCreated() + ") " + event.getChannel().getName() +" " + event.getAuthor().getName() +": "+event.getMessage().getContentDisplay());
        String content = event.getMessage().getContentRaw().trim().toLowerCase();
        String[] commands = UtilCommands.separeCommands(event);

        //Check for Innapropiate Words
        for (String s: Constants.BANNED_WORDS) {
            if(content.contains(s)){
                deployCounterMesures(event);
                return;
            }
        }

        LISTNERS.forEach(command -> {
            for (String s : command.getList()) {
                if(content.contains(s)){
                    command.run(event);
                    return;
                }
            }
        });

        COMMANDS.forEach(command -> {
            if(commands[0].equals(command.getName().toLowerCase())) {
                try {
                    command.run(event);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    event.getChannel().sendMessage("Command failed :(").queue();
                }
            }
        });
    }

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        COMMANDS.forEach(command -> {
            if(command instanceof AdvancedCommand advancedCommand){
//                String ID = event.getComponentId().substring(0, 18);
//                String actionID = event.getComponentId().substring(19, event.getComponentId().length());
                System.out.println(event.getComponentId());
                String[] commands = event.getComponentId().split("\\|");

                try {
                    String testshitplswork = event.getComponentId();
                    System.out.println(testshitplswork);
                    if(advancedCommand.getSelectMenuIds().get(event.getGuild().getIdLong()).contains(event.getComponentId())){
                        advancedCommand.SelectMenuRun(event);
                    }
//                    if((advancedCommand.getSelectMenuIds().get(event.getGuild().getIdLong()).equals(ids[0]))){
//                        advancedCommand.SelectMenuRun(event);
//
//                    }
                }catch (Exception e){
                    System.out.println("Cant be bothered to fix this");
                    e.printStackTrace();
                }
            }
        });
    }

    private void deployCounterMesures(MessageReceivedEvent event) {

        try{
            Channel channel = event.getChannel();
            event.getChannel().sendMessage(Constants.RESPONSES[Constants.rand.nextInt(Constants.RESPONSES.length)]).queue();
            event.getMessage().delete().queue();
        }catch (Exception e){
            System.out.println("Tried deleting already yeeted message");
        }
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
