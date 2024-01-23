package com.kawcix.databases.mysql;

public final class SpecificUserBuilder {
    private String user_id;
    private String server_id;
    private Integer offence;
    private Integer channelDeleteOffence;
    private Integer pingOffence;

    protected SpecificUserBuilder() {
    }

    public SpecificUserBuilder withUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public SpecificUserBuilder withServer_id(String server_id) {
        this.server_id = server_id;
        return this;
    }

    public SpecificUserBuilder withOffence(Integer offence) {
        this.offence = offence;
        return this;
    }

    public SpecificUserBuilder withChannelDeleteOffence(Integer channelDeleteOffence) {
        this.channelDeleteOffence = channelDeleteOffence;
        return this;
    }

    public SpecificUserBuilder withPingOffence(Integer pingOffence) {
        this.pingOffence = pingOffence;
        return this;
    }

    public SpecificUser build() {
        SpecificUser specificUser = new SpecificUser();
        specificUser.setUser_id(user_id);
        specificUser.setServer_id(server_id);
        specificUser.setOffence(offence);
        specificUser.setChannelDeleteOffence(channelDeleteOffence);
        specificUser.setPingOffence(pingOffence);
        return specificUser;
    }
}
