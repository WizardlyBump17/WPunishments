package com.wizardlybump17.wpunishments.implementation.punishable;

import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;

public class DefaultPunishable implements Punishable {

    private final String name;
    private Punishment punishment;

    public DefaultPunishable(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Punishment getPunishment() {
        return punishment;
    }

    @Override
    public void setPunishment(Punishment punishment) {
        this.punishment = punishment;
    }
}
