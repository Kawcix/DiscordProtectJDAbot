package com.kawcix.databases.mysql;

public class SQLSpecificServerParser {
    public String createAddQuery(SpecificServer specificServer) {
        String query;

        query = "INSERT INTO servers(server_id,cooldown)VALUES(" + specificServer.getServer_id() + "," + specificServer.getCooldown() + ")";
        return query;
    }

    public String createCheckQuery(SpecificServer specificServer) {
        String query;

        query = "SELECT EXISTS(SELECT * FROM servers WHERE server_id=" + specificServer.getServer_id() + ")";
        return query;
    }

    public String createUpdateQuery(SpecificServer specificServer) {
        String query;
        query = "UPDATE servers SET cooldown = " + specificServer.getCooldown() + " WHERE server_id = " + specificServer.getServer_id();
        return query;
    }

    public String createGetQuery(SpecificServer specificServer) {
        String query;
        query = "SELECT cooldown FROM servers WHERE server_id=" + specificServer.getServer_id();
        return query;
    }

}
