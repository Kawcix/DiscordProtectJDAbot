package com.kawcix.commands.offence_prevention_system;

import com.kawcix.commands.Icommand;
import com.kawcix.databases.database_manager.IdbManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;
import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class CoolDownSetter implements Icommand {

    final String name = "setservercooldown";

    @Override
    public CommandData register() {
        return Commands.slash(name, "sets a cooldown for offenceprevention system (in minutes)")
                .addOptions(new OptionData(INTEGER,"value","in minutes)",true).setRequiredRange(1,1440)).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        IdbManager idbManager = IdbManager.getConnection();

        if (!event.isFromGuild()) {
            event.reply("You can't use this command here, you can use it only on server you own").queue();
            return;
        }

        if (!(event.getGuild().getOwnerId().equals(event.getUser().getId()))) {
            event.reply("Only server owner can execute this command!").queue();
            return;
        }
        String cooldown = event.getOption("value").getAsString(); // in minutes

        try {
            int cooldownSeconds = Integer.parseInt(cooldown) * 60;
            if (cooldownSeconds <= 0 || cooldownSeconds > 86400) {
                event.reply("value must be a integer from range 1-1440" + "given value: " + cooldown + " is not matching this requirements").queue();
                return;
            }
            idbManager.setCooldown(event.getGuild().getId(), cooldownSeconds);
            if (idbManager.getCooldown(event.getGuild().getId()) == cooldownSeconds) {
                event.reply("Value was successfully changed.").queue();
            } else {
                event.reply("Value wasn't successfully changed.").queue();
            }

        } catch (NumberFormatException e) {
            event.reply("value must be a integer from range 1-1440" + "given value: " + cooldown + " not matching this requiremennts").queue();
        }
    }
}

