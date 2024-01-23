package com.kawcix.commands.useful;

import com.kawcix.commands.Icommand;
import com.kawcix.commands.interaction.GuildLeave;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GuildLeaveLOG implements Icommand, GuildLeave {
    @Override
    public CommandData register() {
        return null;
    }

    @Override
    public String getName() {
        return "guildleavelog";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

    }

    @Override
    public void exec(@NotNull GuildLeaveEvent event) {


    }
}
