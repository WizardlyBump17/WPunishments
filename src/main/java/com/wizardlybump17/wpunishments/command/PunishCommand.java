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

public class PunishCommand extends CustomCommandExecutor {

    public PunishCommand(WPunishments plugin) {
        super(plugin, "punish");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§eUsage: §7/punish <player> <type> [time] [reason]");
            return true;
        }
        try {
            String target = args[0];
            PunishableManager punishableManager = plugin.getPunishableManager();
            Punishable punishable = punishableManager.getOrAdd(target);
            Punishment.PunishmentType type = Punishment.PunishmentType.valueOf(args[1].toUpperCase());
            for (Punishment punishment : punishable.getPunishments())
                if (System.currentTimeMillis() >= punishment.getExpiresIn())
                    punishable.removePunishment(punishment.getType());
            if(args.length == 2) {
                Punishment punishment = Punishment.builder()
                        .type(type)
                        .punished(target)
                        .appliedIn(System.currentTimeMillis())
                        .whoPunished(sender.getName())
                        .permanent(true)
                        .build();
                punishable.addPunishment(punishment);
                punishmentMessages(sender, target, punishment);
                return true;
            }
            int time = Integer.parseInt(args[2]);
            String reason = args.length >= 4 ? StringUtils.join(args, ' ', 3, args.length) : null;
            Punishment punishment = Punishment.builder()
                    .type(type)
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
            sender.sendMessage("§cInvalid punishment time!");
            return true;
        } catch (IllegalArgumentException exception) {
            sender.sendMessage("§cThere's no punishment type with this name (" + args[2] + ")");
            return true;
        }
    }

    private void punishmentMessages(CommandSender sender, String target, Punishment punishment) {
        sender.sendMessage(MessageUtil.getPunishmentMessage(punishment, MessageUtil.PunishmentMessageType.WHO_PUNISHED_APPLIED));
        Player targetPlayer = Bukkit.getPlayerExact(target);
        if (targetPlayer == null) return;
        String targetMessage = MessageUtil.getPunishmentMessage(punishment, MessageUtil.PunishmentMessageType.PUNISHED_APPLIED);
        if (punishment.getType() == Punishment.PunishmentType.MUTE) {
            targetPlayer.sendMessage(targetMessage);
            return;
        }
        targetPlayer.kickPlayer(targetMessage);
    }
}
