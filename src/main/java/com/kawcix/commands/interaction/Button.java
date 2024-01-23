package com.kawcix.commands.interaction;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface Button {
    void exec(ButtonInteractionEvent event);
}
