package com.kawcix.databases.mysql;

public final class SpecificServerBuilder {
    private String server_id;
    private Integer cooldown;

    protected SpecificServerBuilder() {
    }

    public SpecificServerBuilder withServer_id(String server_id) {
        this.server_id = server_id;
        return this;
    }

    public SpecificServerBuilder withCooldown(Integer cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public SpecificServer build() {
        SpecificServer specificServer = new SpecificServer();
        specificServer.setServer_id(server_id);
        specificServer.setCooldown(cooldown);
        return specificServer;
    }
}
