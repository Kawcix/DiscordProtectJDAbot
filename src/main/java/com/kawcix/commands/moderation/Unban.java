package com.kawcix.commands.moderation;

import com.kawcix.commands.Icommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class Unban implements Icommand {
    final String name = "unban";

    @Override
    public CommandData register() {
        return Commands.slash(name, "unban a user")
                .addOption(STRING, "userid", "user's id to ban", true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();

        if (!(event.getMember().hasPermission(Permission.BAN_MEMBERS))) {
            event.reply("You don't have permissions to run this command").queue();
            return;
        }

        User bannedUser = event.getJDA().getUserById((event.getOption("userid").getAsString()));
        if (!(bannedUser == null)) {
            guild.unban(bannedUser).queue();
            event.reply("User " + bannedUser.getName() + " (" + bannedUser.getId() + " ) has been successfully unbanned").queue();
        } else {
            event.reply("User not found").queue();
        }
    }
}
