package com.kawcix.commands.interaction;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;

public interface CommandAutoComplete {
    void exec(CommandAutoCompleteInteractionEvent event);
}
