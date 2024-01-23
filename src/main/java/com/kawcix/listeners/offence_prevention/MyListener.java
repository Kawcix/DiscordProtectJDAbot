package com.kawcix.listeners.offence_prevention;

import com.kawcix.Utils.ApplicationInformationsUtils;
import com.kawcix.Utils.TimeUtil;
import com.kawcix.suspicious_user.SuspiciousUser;
import com.kawcix.suspicious_user.SuspiciousUserGarbageCollector;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;


public class MyListener extends ListenerAdapter {

    public MyListener(SuspiciousUserGarbageCollector suspiciousUserGarbageCollector) {

    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {

        Guild guild = event.getGuild();
        guild.retrieveAuditLogs()
                .limit(1)
                .queueAfter(1, TimeUnit.SECONDS, (logs -> {
                    if (logs.isEmpty()) {
                        return;
                    }



                    AuditLogEntry entry = logs.get(0);

                    boolean isUserFoundInSuspiciousList = false;

                    for (SuspiciousUser x : SuspiciousUser.suspiciousUserList) {
                        if (entry.getUser().getId().equals(x.id) && event.getGuild().getId().equals(x.guild_Id)) {
                            x.offence++;
                            try {
                                x.offensePermitCheck(event, entry);
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
                        suspiciousUser.offence = 1;
                        SuspiciousUser.suspiciousUserList.add(suspiciousUser);
                    }

                    for (SuspiciousUser x : SuspiciousUser.suspiciousUserList) {
                        x.showUserInfo();
                    }

                }));

    }
}

