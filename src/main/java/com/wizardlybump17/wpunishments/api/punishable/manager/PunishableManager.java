package com.wizardlybump17.wpunishments.api.punishable.manager;

import com.wizardlybump17.wpunishments.api.punishable.Punishable;

import java.util.List;

public interface PunishableManager {

    List<Punishable> getPunishables();

    void addPunishable(Punishable punishable);

    boolean hasPunishable(String name);

    void removePunishable(String name);

    default Punishable getPunishable(String name) {
        for (Punishable publishable : getPunishables())
            if (publishable.getName().equalsIgnoreCase(name)) return publishable;
        return null;
    }

    Punishable getOrAdd(String name);
}
