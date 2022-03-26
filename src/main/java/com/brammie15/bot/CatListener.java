package com.brammie15.bot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class CatListener extends ListenerAdapter  {



    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.equals("!sex")) {
            if(message.getAuthor().getId() == "589812478511284226"){
                channel.sendMessage("Noah has been banned from cats (for now)");
                return;
            }

            UtilCommands.sendCat(event);


        }




//            ReadJson reader = new ReadJson();
//            JSONObject json = reader.readJsonFromUrl(url);
        }
    }

