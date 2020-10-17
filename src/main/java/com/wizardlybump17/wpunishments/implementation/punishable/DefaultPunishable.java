package com.wizardlybump17.wpunishments.implementation.punishable;

import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;

import java.util.ArrayList;
import java.util.List;

public class DefaultPunishable implements Punishable {

    private final String name;
    private final List<Punishment> punishments = new ArrayList<>();

    public DefaultPunishable(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Punishment> getPunishments() {
        return punishments;
    }

    @Override
    public void addPunishment(Punishment punishment) {
        for (Punishment activePunishment : getPunishments()) {
            if (activePunishment.getType() != punishment.getType()) continue;
            activePunishment.setDuration(activePunishment.getDuration() + punishment.getDuration());
            return;
        }
        punishments.add(punishment);
    }

    @Override
    public void removePunishment(Punishment.PunishmentType type) {

    }

    @Override
    public boolean hasPunishment(Punishment.PunishmentType type) {
        for (Punishment punishment : getPunishments()) if (punishment.getType() == type) return true;
        return false;
    }
}
