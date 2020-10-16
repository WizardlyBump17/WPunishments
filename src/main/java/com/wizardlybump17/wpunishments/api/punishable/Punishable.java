package com.wizardlybump17.wpunishments.api.punishable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public interface Punishable {

    String getName();

    Punishment getPunishment();

    void setPunishment(Punishment punishment);

    default boolean isPunished() {
        return getPunishment() != null;
    }

    default OfflinePlayer getBukkitPlayer() {
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
            if (offlinePlayer.getName().equals(getName())) return offlinePlayer;
        return null;
    }
}
