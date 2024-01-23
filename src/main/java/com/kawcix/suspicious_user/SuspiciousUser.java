package com.kawcix.suspicious_user;

import com.kawcix.Utils.TimeUtil;
import com.kawcix.databases.database_manager.IdbManager;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SuspiciousUser implements SuspiciousUserDeafults {

    String name;
    String avatarUrl;
    public String id;
    public String guild_Id;

    public int offence = 0; //ban kick
    public int channelDeleteOffence = 0;
    public int pingOffence = 0;

    private String firstOffenceTime = null;

    public static final List<SuspiciousUser> suspiciousUserList = new ArrayList<>();

    private final IdbManager dbManager = IdbManager.getConnection();



    boolean isShouldRemove() {

        Integer cooldown = dbManager.getCooldown(guild_Id);

        Date firstOffenceTimeDate = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            firstOffenceTimeDate = format.parse(this.getFirstOffenceTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long a;
        if (cooldown != null) {
            a = ((firstOffenceTimeDate.getTime() / 1000) + (cooldown));
        } else {
            a = ((firstOffenceTimeDate.getTime() / 1000) + deafultCooldown);
        }
        long b = 0;
        try {
            b = (format.parse(TimeUtil.getActuallTime()).getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return a < b;
    }

    private void react(Reaction reaction, Event event) {

        Guild guild = event.getJDA().getGuildById(reaction.guild_id);
        User user = event.getJDA().getUserById(reaction.user_id);

        switch (reaction.reaction_type) {
            case BAN: {
                user.openPrivateChannel().flatMap(channel -> channel.sendMessage(reaction.message)).queue();
                guild.ban(user, 0, TimeUnit.SECONDS).queue();
                removeFromSuspiciousUserList();
            }
            break;
            case KICK: {
                user.openPrivateChannel().flatMap(channel -> channel.sendMessage(reaction.message)).queue();
                guild.kick(user, "").queue();
                removeFromSuspiciousUserList();
            }
            break;
        }
    }

    public void offensePermitCheck(GuildMemberRemoveEvent event, AuditLogEntry entry) throws ParseException {
        Reaction reaction = new Reaction.ReactionBuilder().
                user_id(entry.getUser().getId())
                .guild_id(event.getGuild().getId())
                .reaction_type(Reaction.Reaction_type.BAN)
                .message("You have exceeded the allowed limit of bans or kicks within the specified time")
                .build();

        Integer offenceLimit = null;

        if (dbManager.isUserExistsInDb(event.getGuild().getId(), entry.getUser().getId())
                & !(dbManager.getOffenceLimit(event.getGuild().getId(), entry.getUser().getId()) == null)) {
            try {
                offenceLimit = dbManager.getOffenceLimit(event.getGuild().getId(), entry.getUser().getId());
            } catch (NullPointerException e) {
                System.out.println(e);
            }
            if (offence < offenceLimit) {
                if (firstOffenceTime != null) {
                    if (this.isShouldRemove()) {
                        removeFromSuspiciousUserList();
                    }
                }
            } else {
                react(reaction, event);
            }
        } else if (offence < deafultOffenceLimit) {
            if (firstOffenceTime != null) {
                if (this.isShouldRemove()) {
                    removeFromSuspiciousUserList();
                }
            }
        } else {
            react(reaction, event);
        }

    }

    public void channelDeletePermitCheck(ChannelDeleteEvent event, AuditLogEntry entry) throws ParseException {
        Reaction reaction = new Reaction.ReactionBuilder().
                user_id(entry.getUser().getId())
                .guild_id(event.getGuild().getId())
                .reaction_type(Reaction.Reaction_type.BAN)
                .message("You have exceeded the allowed limit of number of channel deleted within the specified time")
                .build();



        Integer channelDeleteLimit = null;

        if (dbManager.isUserExistsInDb(event.getGuild().getId(), entry.getUser().getId())
                & !(dbManager.getChannelDeleteLimit(event.getGuild().getId(), entry.getUser().getId()) == null)) {

            try {
                channelDeleteLimit = dbManager.getChannelDeleteLimit(event.getGuild().getId(), entry.getUser().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (channelDeleteOffence < channelDeleteLimit) {
                if (firstOffenceTime != null) {
                    if (this.isShouldRemove()) {

                        removeFromSuspiciousUserList();
                    }
                }
            } else {
                react(reaction, event);
            }
        } else if (channelDeleteOffence < deafultChannelDeleteLimit) {
            if (firstOffenceTime != null) {
                if (this.isShouldRemove()) {
                    removeFromSuspiciousUserList();
                }
            }
        } else {
            react(reaction, event);
        }
    }

    public void pingOffencePermitCheck(MessageReceivedEvent event) throws ParseException {
        Reaction reaction = new Reaction.ReactionBuilder().
                user_id(event.getAuthor().getId())
                .guild_id(event.getGuild().getId())
                .reaction_type(Reaction.Reaction_type.BAN)
                .message("you have exceeded the allowed limit of @everyone or @here pings within the specified time")
                .build();

        Integer pingOffenceLimit = null;
        if (dbManager.isUserExistsInDb(event.getGuild().getId(), event.getAuthor().getId())
                & !(dbManager.getPingOffenceLimit(event.getGuild().getId(), event.getAuthor().getId()) == null)) {

            try {
                pingOffenceLimit = dbManager.getPingOffenceLimit(event.getGuild().getId(), event.getAuthor().getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (pingOffence < pingOffenceLimit) {
                if (firstOffenceTime != null) {
                    if (this.isShouldRemove()) {
                        removeFromSuspiciousUserList();
                    }
                }
            } else {
                react(reaction, event);
            }
        } else if (pingOffence < deafultPingOffenceLimit) {
            if (firstOffenceTime != null) {
                if (this.isShouldRemove()) {
                    removeFromSuspiciousUserList();
                }
            }
        } else {
            react(reaction, event);
        }

    }


    public void setFirstOffenceTime(String time) {
        firstOffenceTime = time;
    }

    public String getFirstOffenceTime() {
        return firstOffenceTime;
    }

    protected void removeFromSuspiciousUserList() {
        suspiciousUserList.remove(this);

    }

    public void showUserInfo() {
        System.out.println("name : " + name);
        System.out.println("avatarUrl: " + avatarUrl);
        System.out.println("id: " + id);
        System.out.println("offence: " + offence);
        System.out.println("first offence time: " + firstOffenceTime);
        System.out.println("channelDeleteOffence " + channelDeleteOffence);
        System.out.println("pingOffence " + pingOffence);

    }

    public static final class SuspiciousUserBuilder {
        private String name;
        private String avatarUrl;
        private String id;
        private String guild_id;

        public SuspiciousUserBuilder() {
        }

        public SuspiciousUserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SuspiciousUserBuilder withAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public SuspiciousUserBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public SuspiciousUserBuilder withGuild_Id(String guild_id) {
            this.guild_id = guild_id;
            return this;
        }


        public SuspiciousUser build() {
            SuspiciousUser suspiciousUser = new SuspiciousUser();
            suspiciousUser.name = this.name;
            suspiciousUser.avatarUrl = this.avatarUrl;
            suspiciousUser.id = this.id;
            suspiciousUser.guild_Id = this.guild_id;

            return suspiciousUser;
        }
    }
}
