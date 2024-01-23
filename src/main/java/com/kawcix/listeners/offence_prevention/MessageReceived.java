package com.kawcix.listeners.offence_prevention;

import com.kawcix.Utils.ApplicationInformationsUtils;
import com.kawcix.Utils.TimeUtil;
import com.kawcix.suspicious_user.SuspiciousUser;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.ParseException;

public class MessageReceived extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        Message message = event.getMessage();

        if (message.getContentRaw().contains("@everyone") || message.getContentRaw().contains("@here")) {

            boolean sth = event.getMember().hasPermission(Permission.MESSAGE_MENTION_EVERYONE);
            if (sth) {
                boolean isUserFoundInSuspiciousList = false;

                for (SuspiciousUser x : SuspiciousUser.suspiciousUserList) {
                    if (event.getAuthor().getId().equals(x.id) && event.getGuild().getId().equals(x.guild_Id)) {
                        x.pingOffence++;
                        try {
                            x.pingOffencePermitCheck(event);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        isUserFoundInSuspiciousList = true;
                        break;
                    }
                }

                if (!isUserFoundInSuspiciousList) {

                    if (event.getAuthor().getId().equals(ApplicationInformationsUtils.AppId)) {
                        return;
                    }

                    SuspiciousUser suspiciousUser = new SuspiciousUser.SuspiciousUserBuilder()
                            .withId(event.getAuthor().getId())
                            .withName(event.getAuthor().getName())
                            .withAvatarUrl(event.getAuthor().getAvatarUrl())
                            .withGuild_Id(event.getGuild().getId())
                            .build();

                    suspiciousUser.setFirstOffenceTime(TimeUtil.getActuallTime());
                    suspiciousUser.pingOffence++;

                    SuspiciousUser.suspiciousUserList.add(suspiciousUser);
                }

            }
        }
    }
}
