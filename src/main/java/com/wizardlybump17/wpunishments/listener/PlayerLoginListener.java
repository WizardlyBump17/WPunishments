package com.wizardlybump17.wpunishments.listener;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.util.MessageUtil;
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
        if (!punishable.isPunished() || !punishable.hasPunishment(Punishment.PunishmentType.BAN)) return;
        Punishment punishment = punishable.getPunishment(Punishment.PunishmentType.BAN);
        if (System.currentTimeMillis() >= punishment.getExpiresIn() && !punishment.isPermanent()) {
            punishable.removePunishment(Punishment.PunishmentType.BAN);
            return;
        }
        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(MessageUtil.getPunishmentMessage(punishment, MessageUtil.PunishmentMessageType.PUNISHED_ACTIVE_PUNISHMENT));
    }
}
