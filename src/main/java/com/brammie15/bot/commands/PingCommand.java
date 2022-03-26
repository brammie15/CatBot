package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {


    @Override
    public String getName() {
        return "Ping";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        event.getJDA().getRestPing()
                .queue(ping -> event.getMessage()
                        .replyFormat("Rest Ping: %sms\nWebsocket Ping: %sms", ping, event.getJDA().getGatewayPing())
                        .mentionRepliedUser(false).queue());
    }

}
