package com.brammie15.bot.commands;

import com.brammie15.bot.Command;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class CatBombCommand extends Command {

    @Override
    public String getName() {
        return "!catbomb";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        String[] commands = UtilCommands.separeCommands(event);
        System.out.println(commands.toString());
        List<User> fuckedUser = event.getMessage().getMentionedUsers();

        try {
            if(fuckedUser.isEmpty()){
                int amountOfTimes = commands[1] == null ? 5 : Integer.parseInt(commands[1]);
                amountOfTimes = Math.min(amountOfTimes, 20);
                for (int i = 0; i < amountOfTimes; i++) {
                    UtilCommands.sendCat(event.getChannel());
                    System.out.println("CatBomb send in: " + event.getChannel().getName());
                }
            }else{
                fuckedUser.forEach(user -> {
                    user.openPrivateChannel().queue(privateChannel -> {
                        int amountOfTimes = commands.length <= 3 ? 5 : Integer.parseInt(commands[2]);
                        amountOfTimes = Math.min(amountOfTimes, 100);
                        for (int i = 0; i < amountOfTimes; i++) {
                            UtilCommands.sendCat(privateChannel);
                            System.out.println("CatBomb send to: " + user.getName());
                        }
                        privateChannel.sendMessage(event.getAuthor().getName() + " has catbombed you :D").queue();
                    });
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("REEEEEEEEEEEEEEEEEE THIS NOGGA FUCKING UP");
        }





        /*if (user != null) {
            user.openPrivateChannel().queue(privateChannel -> {
                for (int i = 0; i < 5; i++) {
                    UtilCommands.sendCat(privateChannel);
                    System.out.println("CatBomb send to: " + user.getName());
                }
                privateChannel.sendMessage(event.getAuthor().getName() + " has catbombed you :D").queue();
            });
        }else{
            event.getChannel().sendMessage("CatBomb Failed :(").queue();
        }*/

    }
}
