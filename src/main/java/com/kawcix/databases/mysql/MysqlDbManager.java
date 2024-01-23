package com.kawcix.databases.mysql;

import com.kawcix.databases.database_manager.IdbManager;

import java.util.List;

public class MysqlDbManager implements IdbManager {

    @Override
    public void setCooldown(String guildId, int cooldown) {
        SpecificServer specificServer = new SpecificServerBuilder()
                .withServer_id(guildId)
                .withCooldown(cooldown)
                .build();
        SpecificServerDAO specificServerDAO = new SpecificServerDAO();
        if (specificServerDAO.serverExistsCheck(specificServer)) {
            specificServerDAO.update(specificServer);

        } else {
            specificServerDAO.add(specificServer);
        }

    }

    @Override
    public void setOffenceLimit(String guildId, String userId, int offenceLimit) {
        SpecificUser specificUser = new SpecificUserBuilder().withOffence(offenceLimit)
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        if (specificUserDAO.usersExistsCheck(specificUser)) {
            specificUserDAO.update(specificUser);
        } else {
            specificUserDAO.add(specificUser);
        }
    }

    @Override
    public void setChannelDeleteLimit(String guildId, String userId, int channelDeleteLimit) {

        SpecificUser specificUser = new SpecificUserBuilder().withChannelDeleteOffence(channelDeleteLimit)
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        if (specificUserDAO.usersExistsCheck(specificUser)) {
            specificUserDAO.update(specificUser);
        } else {
            specificUserDAO.add(specificUser);
        }

    }

    @Override
    public void setPingOffenceLimit(String guildId, String userId, int pingOffenceLimit) {
        SpecificUser specificUser = new SpecificUserBuilder().withPingOffence(pingOffenceLimit)
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        if (specificUserDAO.usersExistsCheck(specificUser)) {
            specificUserDAO.update(specificUser);
        } else {
            specificUserDAO.add(specificUser);
        }

    }

    @Override
    public Integer getCooldown(String guildId) {
        SpecificServer specificServer = new SpecificServerBuilder()
                .withServer_id(guildId)
                .build();
        SpecificServerDAO specificServerDAO = new SpecificServerDAO();
        return specificServerDAO.getCooldown(specificServer);
    }

    @Override
    public Integer getOffenceLimit(String guildId, String userId) {
        SpecificUser specificUser = new SpecificUserBuilder()
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        String value = specificUserDAO.getValue(specificUser, SQLSpecificUserParser.userField.OFFENCE_LIMIT);

        return value == null ? null : Integer.valueOf(value);

    }

    @Override
    public Integer getChannelDeleteLimit(String guildId, String userId) {
        SpecificUser specificUser = new SpecificUserBuilder()
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        String value = specificUserDAO.getValue(specificUser, SQLSpecificUserParser.userField.CHANNEL_DELETE_LIMIT);
        return value == null ? null : Integer.valueOf(value);
    }

    @Override
    public Integer getPingOffenceLimit(String guildId, String userId) {
        SpecificUser specificUser = new SpecificUserBuilder()
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();

        String value = specificUserDAO.getValue(specificUser, SQLSpecificUserParser.userField.PING_OFFENCE_LIMIT);
        return value == null ? null : Integer.parseInt(value);

    }

    @Override
    public boolean isUserExistsInDb(String guildId, String userId) {
        SpecificUser specificUser = new SpecificUserBuilder()
                .withServer_id(guildId)
                .withUser_id(userId)
                .build();
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        return specificUserDAO.usersExistsCheck(specificUser);

    }

    @Override
    public boolean isServerExistsInDb(String guildId) {
        SpecificServer specificServer = new SpecificServerBuilder()
                .withServer_id(guildId)
                .build();
        SpecificServerDAO specificServerDAO = new SpecificServerDAO();
        return specificServerDAO.serverExistsCheck(specificServer);
    }

    @Override
    public List<SpecificUser> getUsersDataOnServer(String serverId) {
        SpecificUserDAO specificUserDAO = new SpecificUserDAO();
        return specificUserDAO.getUsersDataOnServer(serverId);
    }
}
