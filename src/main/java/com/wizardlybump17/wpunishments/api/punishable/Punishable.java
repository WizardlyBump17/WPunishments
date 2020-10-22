package com.wizardlybump17.wpunishments.api.punishable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;

public interface Punishable {

    String getName();

    List<Punishment> getPunishments();

    void addPunishment(Punishment punishment);

    void removePunishment(Punishment.PunishmentType type);

    default boolean isPunished() {
        return !getPunishments().isEmpty();
    }

    boolean hasPunishment(Punishment.PunishmentType type);

    default Punishment getPunishment(Punishment.PunishmentType type) {
        for (Punishment punishment : getPunishments()) if (punishment.getType() == type) return punishment;
        return null;
    }
}
