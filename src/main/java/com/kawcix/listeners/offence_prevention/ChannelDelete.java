package com.kawcix.listeners.offence_prevention;

import com.kawcix.Utils.ApplicationInformationsUtils;
import com.kawcix.Utils.TimeUtil;
import com.kawcix.suspicious_user.SuspiciousUser;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.text.ParseException;

public class ChannelDelete extends ListenerAdapter {
    @Override
    public void onChannelDelete(@Nonnull ChannelDeleteEvent event) {
        Guild guild = event.getGuild();

        guild.retrieveAuditLogs()
                .type(ActionType.CHANNEL_DELETE)
                .limit(1)
                .queue(list -> {

                    if (list.isEmpty()) return;
                    AuditLogEntry entry = list.get(0);

                    boolean isUserFoundInSuspiciousList = false;

                    for (SuspiciousUser x : SuspiciousUser.suspiciousUserList) {
                        if (entry.getUser().getId().equals(x.id) && event.getGuild().getId().equals(x.guild_Id)) {
                            x.channelDeleteOffence++;
                            try {
                                x.channelDeletePermitCheck(event, entry);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            isUserFoundInSuspiciousList = true;
                            break;
                        }
                    }

                    if (!isUserFoundInSuspiciousList) {
                        if (entry.getUser().getId().equals(ApplicationInformationsUtils.AppId)) {
                            return;
                        }

                        SuspiciousUser suspiciousUser = new SuspiciousUser.SuspiciousUserBuilder()
                                .withId(entry.getUser().getId())
                                .withName(entry.getUser().getName())
                                .withAvatarUrl(entry.getUser().getAvatarUrl())
                                .withGuild_Id(event.getGuild().getId())
                                .build();

                        suspiciousUser.setFirstOffenceTime(TimeUtil.getActuallTime());
                        suspiciousUser.channelDeleteOffence++;

                        SuspiciousUser.suspiciousUserList.add(suspiciousUser);
                    }

                });

    }
}
