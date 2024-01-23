package com.kawcix.commands.interaction;

import net.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;

public interface SelectMenu {
    void exec(SelectMenuInteraction event);
}
