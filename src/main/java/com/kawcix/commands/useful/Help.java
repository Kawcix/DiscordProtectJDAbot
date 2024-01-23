package com.kawcix.commands.useful;

import com.kawcix.commands.Icommand;
import com.kawcix.commands.interaction.Button;
import com.kawcix.databases.database_manager.IdbManager;
import com.kawcix.databases.mysql.SpecificUser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.awt.*;
import java.util.List;

public class Help implements Icommand, Button {
    final String name = "help";

    @Override
    public CommandData register() {
        return Commands.slash(name, "show help").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

       IdbManager idbManager = IdbManager.getConnection();
        List<SpecificUser> list = idbManager.getUsersDataOnServer(event.getGuild().getId());


        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Help")
                .setDescription("**Hello!** This bot is designed to safeguard your server against the misuse of admin privileges, such as removing users through bans and kicks, spamming mentions (@everyone @here), and deleting channels.\n" +
                        "\n" +
                        "How does it work?\n" +
                        "\n" +
                        "All server members have default values:\n" +
                        "\n" +
                        "    Default Offence Limit (ban and kick): 5\n" +
                        "    Default Channel Delete Limit: 5\n" +
                        "    Default Ping Offence Limit: 5\n" +
                        "\n" +
                        "For instance, if a user bans 5 other server members within a specific time (default 900 seconds - 15 minutes), they will be automatically removed from the server.\n" +
                        "\n" +
                        "You can customize your own cooldown value using the command \"setservercooldown.\" Let's assume you set it to 15 minutes. Now, any member who exceeds at least one limit within 15 minutes will be removed from the server. If they don't exceed it, their \"offenses\" will reset after 15 minutes.\n" +
                        "\n" +
                        "\n" +
                        "You can set specific limits for specific users.\n" +
                        "\n" +
                        "I hope this bot won't have the occasion to intervene in this server! :>")
                .setColor(Color.blue)
                .build();

        event.replyEmbeds(embed).addActionRow(
                net.dv8tion.jda.api.interactions.components.buttons.Button.primary("fullhelp", "Full help"), // Button with only a label
                net.dv8tion.jda.api.interactions.components.buttons.Button.success("emoji", Emoji.fromUnicode("U+1F600")) // Button with only an emoji
        ).queue();
    }

    @Override
    public void exec(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("fullhelp")) {
            event.reply("maybe soon").queue(); // send a message in the channel
        } else if (event.getComponentId().equals("emoji")) {
            event.editMessage(":>").queue(); // update the message

        }
    }
}
