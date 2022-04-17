package com.brammie15.bot;

import com.google.gson.JsonObject;
import jdk.jfr.Event;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class UtilCommands {

    public static void sendCat(MessageChannel channel){
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
    public static void sendCat(MessageReceivedEvent event){
        UtilCommands.sendCat(event.getChannel());
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
    public static Constants.storeReturn addToRecievers(User user){
        File file = new File("catRecievers/catRecievers.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains(user.getId())){
                    return Constants.storeReturn.ALREADY_EXISTS;
                }else{
                    try{
                        FileWriter sexFile = new FileWriter("catRecievers/catRecievers.txt");
                        BufferedWriter output = new BufferedWriter(sexFile);
                        output.write(user.getId());
                        System.out.println("Added " + user.getName() + " To the hit list");
                    }catch (Exception e){
                        e.printStackTrace();
                        return Constants.storeReturn.FAILED;
                    }
                    return Constants.storeReturn.SUCCES;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return Constants.storeReturn.SEX;
    }

    public static String[] separeCommands(MessageReceivedEvent e){
        String regex = "(?<!(\"|').{0,255}) | (?!.*\\1.*)";
        return e.getMessage().getContentRaw().split(regex);
    }

    public static void sendMessage(JDA jda, User user, String content) {
        storeSentDm(jda,user,content);
        user.openPrivateChannel().queue(channel -> channel.sendMessage(content).queue());
    }
    private static void StoreRecievedDm(MessageReceivedEvent event) {
        if(!(event.getChannelType() == ChannelType.PRIVATE)) {
            UtilCommands.storeToFile(event.getChannel().getName(),  event.getAuthor(), event.getMessage().getContentRaw());
            return;
        }
        User user = event.getAuthor();
        UtilCommands.storeToFile(user.getName(), user, event.getMessage().getContentRaw());
    }

    private static void storeSentDm(JDA jda, User user, String message) {
        UtilCommands.storeToFile(user.getName(), jda.getSelfUser(), message);
    }


}
