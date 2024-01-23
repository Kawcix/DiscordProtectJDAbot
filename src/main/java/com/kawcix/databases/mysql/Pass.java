package com.kawcix.databases.mysql;

import io.github.cdimascio.dotenv.Dotenv;

public class Pass {

    final Dotenv passes = Dotenv.load();

    private final String DBURL = passes.get("DBURL");
    private final String DBUSER = passes.get("DBUSER");
    private final String DBPASS = passes.get("DBPASS");
    private final String DBDRIVER = passes.get("DBDRIVER");

    public Pass() {
        if (DBPASS == null || DBDRIVER == null || DBURL == null || DBUSER == null) {
            System.out.println("failed to load database passes");
        }

    }

    public String getDBURL() {
        return DBURL;
    }

    public String getDBUSER() {
        return DBUSER;
    }

    public String getDBPASS() {
        return DBPASS;
    }

    public String getDBDRIVER() {
        return DBDRIVER;
    }
}
