package com.wizardlybump17.wpunishments.implementation.punishable.manager;

import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.implementation.punishable.DefaultPunishable;

import java.util.ArrayList;
import java.util.List;

public class DefaultPunishableManager implements PunishableManager {

    private final List<Punishable> punishables = new ArrayList<>();

    @Override
    public List<Punishable> getPunishables() {
        return punishables;
    }

    @Override
    public void addPunishable(Punishable punishable) {
        if (hasPunishable(punishable.getName())) return;
        punishables.add(punishable);
    }

    @Override
    public boolean hasPunishable(String name) {
        for (Punishable punishable : punishables) if (punishable.getName().equalsIgnoreCase(name)) return true;
        return false;
    }

    @Override
    public void removePunishable(String name) {
        if (!hasPunishable(name)) return;
        punishables.remove(getPunishable(name));
    }

    @Override
    public Punishable getOrAdd(String name) {
        if (hasPunishable(name)) return getPunishable(name);
        Punishable punishable = new DefaultPunishable(name);
        addPunishable(punishable);
        return punishable;
    }
}
