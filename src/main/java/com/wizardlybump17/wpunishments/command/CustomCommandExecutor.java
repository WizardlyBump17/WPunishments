package com.wizardlybump17.wpunishments.command;

import com.wizardlybump17.wpunishments.WPunishments;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;

@Getter
abstract class CustomCommandExecutor implements CommandExecutor {

    protected final WPunishments plugin;
    private final String commandName;

    public CustomCommandExecutor(WPunishments plugin, String commandName) {
        this.plugin = plugin;
        this.commandName = commandName;
        plugin.getCommand(commandName).setExecutor(this);
        plugin.getLogger().info("Registered the command executor of " + commandName);
    }
}
