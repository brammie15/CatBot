package com.brammie15.bot.commands;


import com.brammie15.bot.Command;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AnoyOwnerCommand extends Command {


    @Override
    public String getName() {
        return "!a";
    }

    @Override
    public void run(MessageReceivedEvent event) throws IOException, InterruptedException {
        String[] commands = UtilCommands.separeCommands(event);
        for (String command : commands) {
            System.out.println(command);
        }
        URL url = new URL("https://ntfy.sh/");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("PUT");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = "{\n    \"topic\": \"sex\",\n    \"message\": \"%s\",\n    \"title\": \"%s\",\n   \"priority\": 5,\n \"click\": \"%s\"\n}";
        String string = String.format(data, commands[1].replace("\"", ""), event.getAuthor().getName() + " Said:",event.getMessage().getJumpUrl());
        System.out.println(string);
        byte[] out = string.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }




}
