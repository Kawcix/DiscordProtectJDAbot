package com.kawcix.commands;

import com.kawcix.commands.interaction.*;
import com.kawcix.commands.moderation.Unban;
import com.kawcix.commands.offence_prevention_system.CoolDownSetter;
import com.kawcix.commands.offence_prevention_system.OffenceSetter;
import com.kawcix.commands.offence_prevention_system.UsersList;
import com.kawcix.commands.useful.GuildJoinLOG;
import com.kawcix.commands.useful.GuildLeaveLOG;
import com.kawcix.commands.useful.Help;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenuInteraction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CommandManager extends ListenerAdapter {

    public static JDA api;





    public CommandManager() {
        addcommand(new OffenceSetter());
        addcommand(new CoolDownSetter());
        addcommand(new Help());
        addcommand(new UsersList());

    }

    public static final Map<String, Icommand> commands = new HashMap<>();

    private void addcommand(Icommand cmd) {
        if (commands.containsKey(cmd.getName())) {
            System.out.println("Duplicated command: " + cmd.getName() + " in " + cmd.getClass().getName() + " replaced by " + commands.get(cmd.getName()).getClass().getName());
            System.exit(2137);
        }
        commands.put(cmd.getName(), cmd);
    }

    public static void registerCommandsGlobal() {



        List<Icommand> list = new ArrayList<>(commands.values());

        for (Icommand s : list) {

            CommandData commandData =  s.register();

            if(commandData != null){
                api.upsertCommand(commandData).queue();
            }

        }

    }

    public static void registerCommandsGuild(){
        List<Icommand> list = new ArrayList<>(commands.values());
        List<net.dv8tion.jda.api.entities.Guild> guilds = api.getGuilds();

        for(Guild x: guilds) {
            CommandListUpdateAction updateAction = x.updateCommands();
            List<CommandData> commandDataList = list.stream()
                    .map(Icommand::register)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            updateAction.addCommands(commandDataList);
            updateAction.queue();





        }

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Thread thread = new Thread(() -> {
            if (!event.getUser().isBot()) {
                Icommand command = commands.get(event.getName());
                if (command != null) {
                    try {
                        command.execute(event);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                }
            }
        });
        thread.start();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        Thread thread = new Thread(() -> {
            CommandAutoComplete commandAutoComplete = (CommandAutoComplete) commands.get(event.getName());
            commandAutoComplete.exec(event);
        });
        thread.start();
    }

    @Override
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {
        Thread thread = new Thread(() -> {
            List<Icommand> list = new ArrayList<>(commands.values());

            for (Icommand s : list) {
                if (s instanceof Button) {
                    ((Button) s).exec(event);
                }
            }
        });
        thread.start();

    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        Thread thread = new Thread(() -> {
        List<Icommand> list = new ArrayList<>(commands.values());

        super.onGuildJoin(event);

        for (Icommand s : list) {
            if (s instanceof GuildJoin) {
                ((GuildJoin) s).exec(event);
            }
        }

        });
        thread.start();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        Thread thread = new Thread(() -> {
            List<Icommand> list = new ArrayList<>(commands.values());

            super.onGuildLeave(event);

            for (Icommand s : list) {
                if (s instanceof GuildLeave) {
                    ((GuildLeave) s).exec(event);
                }
            }

        });
        thread.start();

    }

}
