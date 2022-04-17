package com.brammie15.bot.commands;

import com.brammie15.bot.AdvancedCommand;
import com.brammie15.bot.Command;
import com.brammie15.bot.Constants;
import com.brammie15.bot.UtilCommands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AdvancedSendCommand extends AdvancedCommand {
    public Map<Long, List<String>> componentIds = new HashMap<Long, List<String>>();
    public String[] actionRows = {"ServerSelect", "ChannelSelect"};

    @Override
    public String getName() {
        return "!asend";
    }

    @Override
    public void run(MessageReceivedEvent event) {
        List<Guild> servers = event.getJDA().getGuilds();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Server list");
        builder.setColor(Color.PINK);
        int index = 0;

        for (Guild g: servers) {
            builder.addField(g.getName(),"", false);
            index++;
        }

        List<SelectOption> options = new ArrayList<SelectOption>();
        for (Guild g: servers) {
            options.add(SelectOption.of(g.getName(),g.getId()));
        }

        event.getMessage().replyEmbeds(builder.build()).queue(message -> {
            componentIds.computeIfAbsent(event.getGuild().getIdLong(), id -> new ArrayList<>());

            List<String> temp = componentIds.get(event.getGuild().getIdLong());

            temp.add(message.getId() + "|" + actionRows[0]);
            SelectMenu menu = SelectMenu.create(message.getId() + "|" + actionRows[0]).addOptions(options).build();
            System.out.println(temp.toString());
            componentIds.put(event.getGuild().getIdLong(), temp);
            message.editMessageEmbeds(message.getEmbeds()).setActionRow(menu).queue();
        });

        //event.getMessage().reply("res").setActionRow(SelectMenu.create("Sex").addOption("Test","Mother").build()).queue();


    }


    @Override
    public Map<Long, List<String>> getSelectMenuIds() {
        return componentIds;
    }

    @Override
    public void SelectMenuRun(SelectMenuInteractionEvent event) {
        String[] commands = event.getComponentId().split("\\|");

        if(commands[1].equals(actionRows[0])){ //Server Selector
           printChannelsInServer(event);
        }
        if(commands[1].equals(actionRows[1])){
            getMessageToSend(event);
        }

//        componentIds.get(event.getGuild().getIdLong()).forEach(s -> {
//            event.reply("fuck you ill do this tommorow cuz sex").queue();
//        });
//        String[] ids = event.getComponentId().split("/|");
//        for (String id : ids) {
//            event.getChannel().sendMessage(id).queue();
//        }

    }

    private void getMessageToSend(SelectMenuInteractionEvent event) {
        for (String value : event.getValues()) {
            event.getChannel().sendMessage(event.getJDA().getTextChannelById(value).getName()).queue();
        }

        
    }


    void printChannelsInServer(SelectMenuInteractionEvent event){
        event.getChannel().sendMessage((String.valueOf(event.getResponseNumber()))).queue();

        for (SelectOption selectedOption : event.getSelectedOptions()) {
            event.getChannel().sendMessage(selectedOption.getValue()).queue();
        }

        Guild selectedSever = event.getJDA().getGuildById(event.getSelectedOptions().get(0).getValue());
        event.getChannel().sendMessage(selectedSever.getName()).queue();

        List<GuildChannel> channels = selectedSever.getChannels();
        List<TextChannel> textChannels = new ArrayList<>();
        for (GuildChannel channel : channels) {
            if(channel.getType() == ChannelType.TEXT){
                textChannels.add((TextChannel) channel);
            }
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Channel List");
        builder.setColor(Color.GREEN);
        int index = 0;
        boolean isTitle = true;
        for (int i = 0; i < textChannels.size(); i+=2) {
            if(i + 2 > textChannels.size()){
                builder.addField(textChannels.get(i).getName(), "", false);
            }else{
                builder.addField(textChannels.get(i).getName(), textChannels.get(i + 1).getName(), false);
            }
        }
        List<SelectOption> options = new ArrayList<SelectOption>();
        if (textChannels.size() > 25){ //Cant make em all in 1 selectMenu
            for (int i = 0; i < 24; i++) {
                options.add(SelectOption.of(textChannels.get(i).getName(), textChannels.get(i).getId()));
            }
            options.add(SelectOption.of("Next Page","NextPage"));
        }else{
            for (TextChannel c: textChannels) {
                options.add(SelectOption.of(c.getName(),c.getId()));
            }
        }

        event.getMessage().replyEmbeds(builder.build()).queue(message -> {
            componentIds.computeIfAbsent(event.getGuild().getIdLong(), id -> new ArrayList<>());

            List<String> temp = componentIds.get(event.getGuild().getIdLong());

            temp.add(message.getId() + "|" + actionRows[1]);
            SelectMenu menu = SelectMenu.create(message.getId() + "|" + actionRows[1]).addOptions(options).build();
            System.out.println(temp.toString());
            componentIds.put(event.getGuild().getIdLong(), temp);
            message.editMessageEmbeds(message.getEmbeds()).setActionRow(menu).queue();
        });
    }
}
