package com.wizardlybump17.wpunishments.command;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand extends CustomCommandExecutor {

    public BanCommand(WPunishments plugin) {
        super(plugin, "ban");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§eUsage: §7/ban <player> [time] [reason]");
            return true;
        }
        try {
            String target = args[0];
            PunishableManager punishableManager = plugin.getPunishableManager();
            Punishable punishable = punishableManager.getOrAdd(target);
            for (Punishment punishment : punishable.getPunishments())
                if (System.currentTimeMillis() >= punishment.getExpiresIn())
                    punishable.removePunishment(punishment.getType());
            if (args.length == 1) {
                Punishment punishment = Punishment.builder()
                        .type(Punishment.PunishmentType.BAN)
                        .punished(target)
                        .appliedIn(System.currentTimeMillis())
                        .whoPunished(sender.getName())
                        .permanent(true)
                        .build();
                punishable.addPunishment(punishment);
                punishmentMessages(sender, target, punishment);
                return true;
            }
            int time = Integer.parseInt(args[1]);
            String reason = args.length >= 3 ? StringUtils.join(args, ' ', 2, args.length) : null;
            Punishment punishment = Punishment.builder()
                    .type(Punishment.PunishmentType.BAN)
                    .punished(target)
                    .appliedIn(System.currentTimeMillis())
                    .duration(time <= 0 ? 0 : time * 1000)
                    .reason(reason)
                    .whoPunished(sender.getName())
                    .permanent(time <= 0)
                    .build();
            punishable.addPunishment(punishment);
            punishmentMessages(sender, target, punishment);
            return true;
        } catch (NumberFormatException exception) {
            sender.sendMessage("§cInvalid ban time!");
            return true;
        }
    }

    private void punishmentMessages(CommandSender sender, String target, Punishment punishment) {
        sender.sendMessage(MessageUtil.getPunishmentMessage(punishment, MessageUtil.PunishmentMessageType.WHO_PUNISHED_APPLIED));
        Player targetPlayer = Bukkit.getPlayerExact(target);
        if (targetPlayer == null) return;
        String targetMessage = MessageUtil.getPunishmentMessage(punishment, MessageUtil.PunishmentMessageType.PUNISHED_APPLIED);
        targetPlayer.kickPlayer(targetMessage);
    }
}
