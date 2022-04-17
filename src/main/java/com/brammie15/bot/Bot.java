package com.brammie15.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java -jar bot.jar <token>");
            System.exit(1);
        }
        JDA api = JDABuilder.createDefault(args[0]).build();
        api.addEventListener(new CatListener());

//        api.addEventListener(new dmListener());
        api.addEventListener(new CommandSex());

    }
}


