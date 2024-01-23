package com.kawcix.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public interface Icommand {
    CommandData register();

    String getName();

    void execute(SlashCommandInteractionEvent event);
}
