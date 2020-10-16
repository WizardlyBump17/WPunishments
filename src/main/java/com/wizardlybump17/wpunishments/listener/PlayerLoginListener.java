package com.wizardlybump17.wpunishments.listener;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener extends CustomListener {

    public PlayerLoginListener(WPunishments plugin) {
        super(plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        PunishableManager punishableManager = plugin.getPunishableManager();
        if (!punishableManager.hasPunishable(player.getName())) return;
        Punishable punishable = punishableManager.getPunishable(player.getName());
        if (!punishable.isPunished()) return;
        Punishment punishment = punishable.getPunishment();
        if (punishment.getType() != Punishment.PunishmentType.BAN) return;
        if (System.currentTimeMillis() >= punishment.getExpiresIn() && !punishment.isPermanent()) {
            punishable.setPunishment(null);
            return;
        }
        String message = StringUtils.join(new String[]{
                "§cYou was banned by " + punishment.getWhoPunished() + "!",
                "§cDetails:",
                "§2Time left: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(System.currentTimeMillis(), punishment.getExpiresIn())),
                "§2Reason: §f" + (punishment.getReason() == null ? "no reason given" : punishment.getReason())
        }, "\n");
        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(message);
    }
}
