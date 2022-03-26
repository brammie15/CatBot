package com.brammie15.bot;

import com.brammie15.bot.commands.PingCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class dmListener extends ListenerAdapter {
    public void sendMessage(JDA jda, User user, String content) {
        storeSentDm(jda,user,content);
        user.openPrivateChannel().queue(channel -> channel.sendMessage(content).queue());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getMessage().getAuthor().isBot()) return;
        StoreRecievedDm(event);

        System.out.println(event.getAuthor().getName() +": "+event.getMessage().getContentDisplay());
        Message message = event.getMessage();

        if(message.getContentRaw().equals("!SignUp")){
            event.getChannel().sendMessage("Hello, We are currently working our shit out (As you do)").queue();
            event.getChannel().sendMessage("But however you can get a fancy cat pic from the Awesome creator (Super hot Bram ofc)").queue();
            UtilCommands.sendCat(event);
        }
        if(message.getContentRaw().equals("!cat")){
            event.getChannel().sendMessage("This command will eventually be deprecated").queue();
            UtilCommands.sendCat(event);
        }

        String regex = "(?<!(\"|').{0,255}) | (?!.*\\1.*)";
        String[] commands = event.getMessage().getContentRaw().split(regex);
        if(commands[0].equals("!send") && message.getAuthor().getId().equals("535145705698885633")) {
            if(commands.length < 3){
                event.getChannel().sendMessage("Ey neef je command is kkr verkeerd loser eig").queue();
                return;
            }
            commands[2] = commands[2].replaceAll("\"","");
            StringBuilder output = new StringBuilder();
            if (commands[0].equals("!send")) {
                for (String command : commands) {
                    output.append(command);
                }
            }
            event.getChannel().sendMessage(output.toString()).queue();
            event.getJDA().retrieveUserById(commands[1]).queue(user -> sendMessage(event.getJDA(),user, commands[2]));
        }

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
