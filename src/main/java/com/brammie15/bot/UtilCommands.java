package com.brammie15.bot;

import com.google.gson.JsonObject;
import jdk.jfr.Event;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UtilCommands {
    public int sex;
    public static void sendCat(MessageReceivedEvent event){
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        try {
            final URLConnection connection = new URL("https://cataas.com/cat?json=true").openConnection();
            JsonObject json = Constants.gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonObject.class);
            String url = json.get("url").getAsString();
            try {
                URL catUrl = new URL("https://cataas.com" + url);
                Image image = ImageIO.read(catUrl);
//                    channel.sendMessage("No more cats").queue();
                channel.sendFile(new URL(catUrl.toString()).openStream(), "cat.png").queue();
            } catch (IOException e) {
                // handle IOException
            }


        } catch (Exception e) {
            channel.sendMessage("Something Fucked up Big Time").queue();
            e.printStackTrace();
        }
    }

    public static void storeToFile(String fileName, User user, String content){
        try{
            if(!Files.exists(Paths.get("logs/" + fileName + ".txt").normalize())){
                Files.createFile(Path.of("logs/" + fileName + ".txt").normalize());
                System.out.println("Made new user file for: " + user.getName());
            }
            Files.write(Paths.get("logs/"+fileName+".txt").normalize(), (user.getName() + ": " + content + "\n").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e){
            e.printStackTrace();
            try{

                if(!Files.exists(Paths.get("logs/" + user.getId() + ".txt").normalize())){
                    Files.createFile(Path.of("logs/" + user.getId() + ".txt").normalize());
                    System.out.println("Made new user file for: " + user.getName());
                }
                Files.write(Paths.get("logs/"+user.getName()+".txt").normalize(), (user.getName() + ": " + content + "\n").getBytes(), StandardOpenOption.APPEND);
            }catch (IOException ee){
                ee.printStackTrace();
                System.out.println("Couldnt save data cuz something fucked up big time :D");
            }

        }
    }


}
