package com.brammie15.bot.commands.Listners;

import com.brammie15.bot.MessageListener;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SadListner extends MessageListener {
    private static final String[] SAD_WORDS_LIST = {
            "sad",
            "crying",
            "hug",
            "depresso",
            ":("
    };

    @Override
    public String[] getList() {
        return SAD_WORDS_LIST;
    }

    @Override
    public void run(MessageReceivedEvent event) {
        for (int i = 0; i < 5; i++) {
            UtilCommands.sendCat(event);
        }
    }
}
