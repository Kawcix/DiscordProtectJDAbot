package com.kawcix.databases.mysql;

public class SpecificUser {

    String user_id = null;
    String server_id = null;
    Integer offence = null;
    Integer channelDeleteOffence = null;
    Integer pingOffence = null;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public Integer getOffence() {
        return offence;
    }

    public void setOffence(Integer offence) {
        this.offence = offence;
    }

    public Integer getChannelDeleteOffence() {
        return channelDeleteOffence;
    }

    public void setChannelDeleteOffence(Integer channelDeleteOffence) {
        this.channelDeleteOffence = channelDeleteOffence;
    }

    public Integer getPingOffence() {
        return pingOffence;
    }

    public void setPingOffence(Integer pingOffence) {
        this.pingOffence = pingOffence;
    }
}
