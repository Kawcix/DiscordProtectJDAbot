package com.kawcix.databases.database_manager;

import com.kawcix.databases.mysql.MysqlDbManager;
import com.kawcix.databases.mysql.SpecificUser;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public interface IdbManager {

    void setCooldown(String guildId, int cooldown);
    //int deafultCooldown = 900; // in seconds

    //int deafultOffenceLimit = 5; //ban and kick
    void setOffenceLimit(String guildId, String userId, int offenceLimit);

    //int deafultChannelDeleteLimit = 5;
    void setChannelDeleteLimit(String guildId, String userId, int channelDeleteLimit);

    //int deafultPingOffenceLimit = 5;
    void setPingOffenceLimit(String guildId, String userId, int pingOffenceLimit);

    Integer getCooldown(String guildId);

    Integer getOffenceLimit(String guildId, String userId);

    Integer getChannelDeleteLimit(String guildId, String userId);

    Integer getPingOffenceLimit(String guildId, String userId);

    boolean isUserExistsInDb(String guildId, String userId);

    boolean isServerExistsInDb(String guildId);

    List<SpecificUser> getUsersDataOnServer(String serverId);

    static IdbManager getConnection() {
        Dotenv config = Dotenv.load();
        final String DATABASE_USED = config.get("DATABASE_USED");
        //noinspection SwitchStatementWithTooFewBranches
        switch (DATABASE_USED) {
            case "mysql": {
                return new MysqlDbManager();
            }
            default: {
                return null;
            }
        }
    }
}
