package com.kawcix.commands.offence_prevention_system;

import com.kawcix.commands.CommandManager;
import com.kawcix.commands.Icommand;
import com.kawcix.databases.database_manager.IdbManager;
import com.kawcix.databases.mysql.MysqlDbManager;
import com.kawcix.databases.mysql.SpecificUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.List;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;
import static net.dv8tion.jda.api.interactions.commands.OptionType.USER;

public class UsersList implements Icommand {
    @Override
    public CommandData register() {
        return Commands.slash(getName(), "show list of users that have another offences limits").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }

    @Override
    public String getName() {
        return "userlist";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (!event.getGuild().getOwnerId().equals(event.getMember().getId())) {
           event.reply("Only server owner can use this command.").queue();
        }

        event.deferReply().queue();
        IdbManager db = IdbManager.getConnection();
        List<SpecificUser> users =  db.getUsersDataOnServer(event.getGuild().getId());

        if(users.isEmpty()) {
            event.getHook().sendMessage("You have not set any specificial values for users yet (use setoffencepreventionforuser command ) ").queue();
            return;
        }



        for(SpecificUser x: users) {
            User user = CommandManager.api.getUserById(x.getUser_id());
            Member member = event.getGuild().getMemberById(x.getUser_id());
            event.getChannel().asTextChannel().sendMessage(" user  -> "+user.getName() + " (user id -> " + user.getId() + ")  avatar url ->  " + user.getAvatarUrl() + " ban and kicks limit = " + x.getOffence() + " channel delete limit = " + x.getChannelDeleteOffence() + " pings limit (everyone and here) = " + x.getPingOffence()).queue();
        }

        Integer serverCooldown = db.getCooldown(event.getGuild().getId());
        event.getHook().sendMessage("that's all your server cooldown is " + serverCooldown + "null means that deafult values are used (5)").queue();







    }
}
