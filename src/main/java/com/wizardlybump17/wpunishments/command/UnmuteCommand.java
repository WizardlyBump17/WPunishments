package com.wizardlybump17.wpunishments.command;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnmuteCommand extends CustomCommandExecutor {

    public UnmuteCommand(WPunishments plugin) {
        super(plugin, "unmute");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("§eUsage: §7/unmute <player>");
            return true;
        }
        PunishableManager punishableManager = plugin.getPunishableManager();
        if(!punishableManager.hasPunishable(args[0])) {
            sender.sendMessage("§cThis player not exists or isn't muted!");
            return true;
        }
        Punishable punishable = punishableManager.getPunishable(args[0]);
        if(!punishable.hasPunishment(Punishment.PunishmentType.MUTE)) {
            sender.sendMessage("§cThis player isn't muted!");
            return true;
        }
        punishable.removePunishment(Punishment.PunishmentType.MUTE);
        sender.sendMessage("§aSuccessfully removed the mute from " + punishable.getName());
        Player target = Bukkit.getPlayerExact(punishable.getName());
        if(target == null) return true;
        target.sendMessage("§a" + sender.getName() + " has removed your mute!");
        return true;
    }
}
