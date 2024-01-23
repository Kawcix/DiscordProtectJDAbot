package com.kawcix.commands.interaction;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;

import javax.annotation.Nonnull;

public interface GuildJoin {
    void exec(@Nonnull GuildJoinEvent event);
}
