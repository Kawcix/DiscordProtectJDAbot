package com.kawcix.commands.offence_prevention_system;

import com.kawcix.commands.Icommand;
import com.kawcix.commands.interaction.CommandAutoComplete;
import com.kawcix.databases.database_manager.IdbManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class OffenceSetter implements Icommand, CommandAutoComplete {

    final String name = "setoffencepreventionforuser";

    @Override
    public CommandData register() {
        return Commands.slash(name, "sets specify value to specify user")
                .addOption(STRING, "option", "Specify Option", true, true)
                .addOption(USER, "user", "Specify user", true) // sussy
//                .addOption(INTEGER,"value", "Specify value (number of allowed offences)", true)
                .addOptions(new OptionData(INTEGER,"value","Specify value (number of allowed offences)",true).setRequiredRange(2,2147483647)).setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        IdbManager idbManager = IdbManager.getConnection();

        if (!event.isFromGuild()) {
            event.reply("\"You can't use this command here, you can use it only on server you own").queue();
            return;
        }

        if (!(event.getGuild().getOwnerId().equals(event.getUser().getId()))) {
            event.reply("Only server owner can run this command").queue();
            return;
        }

        User user = event.getOption("user").getAsUser();
        String guild_id = event.getGuild().getId();
        String user_id = event.getOption("user").getAsString();
        int value = event.getOption("value").getAsInt();
        String option = event.getOption("option").getAsString();

        switch (event.getOption("option").getAsString()) {
            case "offenceLimit": {
                idbManager.setOffenceLimit(guild_id, user_id, value);
                showResult(option, user, event);

            }
            break;
            case "ChannelDeleteLimit": {
                idbManager.setChannelDeleteLimit(guild_id, user_id, value);
                showResult(option, user, event);

            }
            break;
            case "PingOffenceLimit": {
                idbManager.setPingOffenceLimit(guild_id, user_id, value);
                showResult(option, user, event);
            }
            break;
            default: {

            }
        }
    }


    private void showResult(String option, User user, SlashCommandInteractionEvent event) {

        String value = event.getOption("value").getAsString();

        String username = user.getName();

        switch (option) {
            case "offenceLimit": {
                event.reply("Offence limit was changed for " + username + "( " + user.getId() + " )" + "to " + value).queue();
            }
            break;
            case "ChannelDeleteLimit": {
                event.reply("Channel Delete limit was changed for " + username + "( " + user.getId() + " )" + "to " + value).queue();
            }
            break;
            case "PingOffenceLimit": {
                event.reply("Ping offence limit  was changed for " + username + "( " + user.getId() + " )" + "to " + value).queue();
            }
            break;
            default: {

            }
        }
    }

    @Override
    public void exec(CommandAutoCompleteInteractionEvent event) {

        final String[] words2 = new String[]{"offenceLimit", "ChannelDeleteLimit", "PingOffenceLimit"};

        if (event.getName().equals("setoffencepreventionforuser") && event.getFocusedOption().getName().equals("option")) {
            List<Command.Choice> options = Stream.of(words2)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue())) // only display words that start with the user's current input
                    .map(word -> new Command.Choice(word, word)) // map the words to choices
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }
}
