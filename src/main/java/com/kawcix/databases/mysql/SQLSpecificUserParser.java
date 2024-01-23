package com.kawcix.databases.mysql;

import java.util.HashMap;
import java.util.Map;

public class SQLSpecificUserParser {

    public String createAddQuery(SpecificUser specificUser) {
        String query;
        query = "INSERT INTO users (server_id, user_id, offence_limit, channel_delete_limit, ping_offence_limit) " +
                "VALUES(" + specificUser.getServer_id() + "," + specificUser.getUser_id() + "," + specificUser.getOffence() + ","
                + specificUser.getChannelDeleteOffence() + "," + specificUser.getPingOffence() + ")";

        return query;
    }

    public String createDeleteQuery(SpecificUser specificUser) {
        String query;
        query = "DELETE FROM users WHERE server_id = " + specificUser.getServer_id() + " AND " + "user_id = " + specificUser.getUser_id();
        return query;
    }

    public String createUpdateQuery(SpecificUser specificUser) {
        String query;

        Map<String, Integer> fields = new HashMap<>();
        fields.put("offence_limit", specificUser.getOffence());
        fields.put("channel_delete_limit", specificUser.getChannelDeleteOffence());
        fields.put("ping_offence_limit", specificUser.getPingOffence());

        query = "UPDATE users SET ";

        int counter = 0;

        for (Map.Entry<String, Integer> entry : fields.entrySet()) {

            if (entry.getValue() == null) {

            } else {
                if (counter > 0) {
                    query = query + ", ";
                    query = query + entry.getKey() + " " + "= " + entry.getValue();
                } else {
                    query = query + entry.getKey() + " " + "= " + entry.getValue();
                    counter++;
                }
            }
        }

        return query + " WHERE user_id = " + specificUser.getUser_id() + " AND " + "server_id = " + specificUser.getServer_id();
    }

    public String createCheckQuery(SpecificUser specificUser) {
        String query;

        query = "SELECT EXISTS(SELECT * FROM users WHERE server_id=" + specificUser.getServer_id() + " AND user_id = " + specificUser.getUser_id() + ")";
        return query;
    }

    public enum userField {
        OFFENCE_LIMIT,
        CHANNEL_DELETE_LIMIT,
        PING_OFFENCE_LIMIT
    }

    public String createGetValueQuery(SpecificUser specificUser, userField userField) {
        String query;

        switch (userField) {
            case OFFENCE_LIMIT: {
                query = "SELECT offence_limit FROM users WHERE server_id=" + specificUser.getServer_id() + " AND user_id=" + specificUser.getUser_id();
                return query;

            }
            case PING_OFFENCE_LIMIT: {
                query = "SELECT ping_offence_limit FROM users WHERE server_id=" + specificUser.getServer_id() + " AND user_id=" + specificUser.getUser_id();
                return query;

            }
            case CHANNEL_DELETE_LIMIT: {
                query = "SELECT channel_delete_limit FROM users WHERE server_id=" + specificUser.getServer_id() + " AND user_id=" + specificUser.getUser_id();
                return query;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + userField);
        }
    }

}

