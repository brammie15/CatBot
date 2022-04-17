package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class SendCommand extends Command {


    @Override
    public String getName() {
        return "!send";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        String[] commands = UtilCommands.separeCommands(event);
        //&& event.getMessage().getAuthor().getId().equals("535145705698885633")
        if(commands[0].equals("!send") ) {
            if(commands.length < 3){
                event.getChannel().sendMessage("Ey neef je command is kkr verkeerd loser eig").queue();
                return;
            }
            commands[2] = commands[2].replaceAll("\"","");
            event.getChannel().sendMessage("Sending Command").queue();
            try {
                List<User> fuckedUser = event.getMessage().getMentionedUsers();
                fuckedUser.forEach(user -> {
                    user.openPrivateChannel().queue(testUser -> {
                        testUser.sendMessage(commands[2]).queue();
                    });    });
            }catch(Exception e){
                e.printStackTrace();
            }
            //event.getJDA().retrieveUserById(commands[1]).queue(user -> sendMessage(event.getJDA(),user, commands[2]));
        }
    }

    public void sendMessage(JDA jda, User user, String content) {
        storeSentDm(jda,user,content);
        user.openPrivateChannel().queue(channel -> channel.sendMessage(content).queue());
    }
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
