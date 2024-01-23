package com.kawcix.commands.interaction;

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import org.jetbrains.annotations.NotNull;

public interface GuildLeave {
    void exec(@NotNull GuildLeaveEvent event);
}
