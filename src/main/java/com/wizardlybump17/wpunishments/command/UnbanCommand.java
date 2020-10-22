package com.wizardlybump17.wpunishments.command;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UnbanCommand extends CustomCommandExecutor {

    public UnbanCommand(WPunishments plugin) {
        super(plugin, "unban");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("§eUsage: §7/unban <player>");
            return true;
        }
        PunishableManager punishableManager = plugin.getPunishableManager();
        if(!punishableManager.hasPunishable(args[0])) {
            sender.sendMessage("§cThis player not exists or isn't banned!");
            return true;
        }
        Punishable punishable = punishableManager.getPunishable(args[0]);
        if(!punishable.hasPunishment(Punishment.PunishmentType.BAN)) {
            sender.sendMessage("§cThis player isn't banned!");
            return true;
        }
        punishable.removePunishment(Punishment.PunishmentType.BAN);
        sender.sendMessage("§aSuccessfully removed the ban from " + punishable.getName());
        return true;
    }
}
