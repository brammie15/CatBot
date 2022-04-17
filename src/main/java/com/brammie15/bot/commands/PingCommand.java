package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {


    @Override
    public String getName() {
        return "!ping";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        event.getJDA().getRestPing()
                .queue(ping -> event.getMessage()
                        .replyFormat("Rest Ping: %sms\nWebsocket Ping: %sms", ping, event.getJDA().getGatewayPing())
                        .mentionRepliedUser(false).queue());
//        System.out.println(event.getJDA().getGuilds().toString());
//        event.getJDA().getGuilds().forEach(guild -> {
//            guild.getTextChannelsByName("Chatten", false).forEach(UtilCommands::sendCat);
//            guild.getTextChannelsByName("Chatten", false).forEach(textChannel -> {
//                textChannel.sendMessage("Hello People").queue();
//            });
//
//        });
    }


}
