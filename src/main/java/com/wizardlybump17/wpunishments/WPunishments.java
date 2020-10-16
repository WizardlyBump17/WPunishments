package com.wizardlybump17.wpunishments;

import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.command.PunishCommand;
import com.wizardlybump17.wpunishments.implementation.punishable.manager.DefaultPunishableManager;
import com.wizardlybump17.wpunishments.listener.AsyncPlayerChatListener;
import com.wizardlybump17.wpunishments.listener.PlayerLoginListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class WPunishments extends JavaPlugin {

    private final PunishableManager punishableManager = new DefaultPunishableManager();

    @Override
    public void onEnable() {
        initEvents();
        initCommands();
    }

    private void initEvents() {
        long start = System.currentTimeMillis();
        getLogger().info("Registering events...");
        new AsyncPlayerChatListener(this);
        new PlayerLoginListener(this);
        getLogger().info("Registered events in " + (System.currentTimeMillis() - start) + "ms");
    }

    private void initCommands() {
        long start = System.currentTimeMillis();
        getLogger().info("Registering commands...");
        new PunishCommand(this);
        getLogger().info("Registered commands in " + (System.currentTimeMillis() - start) + "ms");
    }
}
