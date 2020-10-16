package com.wizardlybump17.wpunishments.listener;

import com.wizardlybump17.wpunishments.WPunishments;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

@Getter
abstract class CustomListener implements Listener {

    protected final WPunishments plugin;

    public CustomListener(WPunishments plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getLogger().info("Registered the events in " + getClass().getSimpleName() + " class");
    }
}
