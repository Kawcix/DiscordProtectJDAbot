package com.kawcix.commands.useful;

import com.kawcix.commands.Icommand;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class GuildJoinLOG implements com.kawcix.commands.interaction.GuildJoin, Icommand {
    @Override
    public void exec(@NotNull GuildJoinEvent event) {



    }

    @Override
    public CommandData register() {
        return null;
    }

    @Override
    public String getName() {
        return "guildjoinlog";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }
}


