package com.kawcix;

import com.kawcix.Utils.ApplicationInformationsUtils;
import com.kawcix.commands.CommandManager;
import com.kawcix.listeners.offence_prevention.ChannelDelete;
import com.kawcix.listeners.offence_prevention.MessageReceived;
import com.kawcix.listeners.offence_prevention.MyListener;
import com.kawcix.suspicious_user.SuspiciousUserGarbageCollector;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordBot {

    public static void main(String[] arguments) throws Exception {
        Dotenv config = Dotenv.load();

        JDA api = JDABuilder
                .createDefault(config.get("TOKEN"))
                .addEventListeners(new MyListener(new SuspiciousUserGarbageCollector()), new MessageReceived(), new ChannelDelete())
                .addEventListeners(new CommandManager())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_BANS)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        api.awaitReady();

        ApplicationInformationsUtils.AppId = api.getSelfUser().getId();
        CommandManager.api = api;
        CommandManager.registerCommandsGlobal();
    }
}
