package com.wizardlybump17.wpunishments.command;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishCommand extends CustomCommandExecutor {

    public PunishCommand(WPunishments plugin) {
        super(plugin, "punish");
    }

    // /punish <player> <time> [type] [reason]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§eUsage: §7/punish <player> <time> [type] [reason]");
            return true;
        }
        if (args.length == 2) {
            if (sender instanceof Player) {
                //chat actions
                return true;
            }
            sender.sendMessage("§eUsage: §7/punish <player> <time> <type> [reason]");
            return true;
        }
        String target = args[0];
        PunishableManager punishableManager = plugin.getPunishableManager();
        Punishable punishable = punishableManager.getOrAdd(target);
        try {
            int time = Integer.parseInt(args[1]);
            Punishment.PunishmentType type = Punishment.PunishmentType.valueOf(args[2].toUpperCase());
            if (punishable.isPunished() && System.currentTimeMillis() >= punishable.getPunishment().getExpiresIn() && !punishable.getPunishment().isPermanent()) punishable.setPunishment(null);
            if (punishable.isPunished() && punishable.getPunishment().getType() == Punishment.PunishmentType.BAN) {
                sender.sendMessage("§cCannot punish banned players!");
                return true;
            }
            String reason = args.length >= 4 ? StringUtils.join(args, ' ', 3, args.length) : null;
            Punishment punishment = new Punishment(type, sender.getName(), time <= 0 ? 0 : System.currentTimeMillis() + time * 1000, System.currentTimeMillis(), reason);
            punishable.setPunishment(punishment);
            sender.sendMessage(new String[]{
                    "§aYou punished " + target + "!",
                    "§aDetails:",
                    "§2Type: §f" + type,
                    "§2Time: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(punishment.getAppliedIn(), punishment.getExpiresIn())),
                    "§2Reason: §f" + (reason == null ? "no reason given" : reason)
            });
            Player targetPlayer = Bukkit.getPlayerExact(target);
            if (targetPlayer == null) return true;
            if (punishment.getType() == Punishment.PunishmentType.MUTE) {
                targetPlayer.sendMessage(new String[]{
                        "§cYou was muted by " + sender.getName() + "!",
                        "§cDetails:",
                        "§2Time: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(punishment.getAppliedIn(), punishment.getExpiresIn())),
                        "§2Reason: §f" + (reason == null ? "no reason given" : reason)
                });
                return true;
            }
            targetPlayer.kickPlayer(StringUtils.join(new String[]{
                            "§cYou was banned by " + sender.getName() + "!",
                            "§cDetails:",
                            "§2Time: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(punishment.getAppliedIn(), punishment.getExpiresIn())),
                            "§2Reason: §f" + (reason == null ? "no reason given" : reason)
                    }, "\n")
            );
            return true;
        } catch (NumberFormatException exception) {
            sender.sendMessage("§cInvalid punishment time!");
            return true;
        } catch (IllegalArgumentException exception) {
            sender.sendMessage("§cThere's no punishment type with this name (" + args[2] + ")");
            return true;
        }
    }
}
