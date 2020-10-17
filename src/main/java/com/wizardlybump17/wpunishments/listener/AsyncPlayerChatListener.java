package com.wizardlybump17.wpunishments.listener;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.util.MessageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener extends CustomListener {

    public AsyncPlayerChatListener(WPunishments plugin) {
        super(plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PunishableManager punishableManager = plugin.getPunishableManager();
        if (!punishableManager.hasPunishable(player.getName())) return;
        Punishable punishable = punishableManager.getPunishable(player.getName());
        if (!punishable.isPunished() || !punishable.hasPunishment(Punishment.PunishmentType.MUTE)) return;
        Punishment punishment = punishable.getPunishment(Punishment.PunishmentType.MUTE);
        if (System.currentTimeMillis() >= punishment.getExpiresIn() && !punishment.isPermanent()) {
            punishable.removePunishment(Punishment.PunishmentType.MUTE);
            return;
        }
        player.sendMessage(MessageUtil.getPunishmentMessage(punishment, MessageUtil.PunishmentMessageType.PUNISHED_ACTIVE_PUNISHMENT));
        event.setCancelled(true);
    }
}
