package com.wizardlybump17.wpunishments.listener;

import com.wizardlybump17.wpunishments.WPunishments;
import com.wizardlybump17.wpunishments.api.punishable.Punishable;
import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import com.wizardlybump17.wpunishments.api.punishable.manager.PunishableManager;
import com.wizardlybump17.wpunishments.util.DateUtil;
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
        if (!punishable.isPunished()) return;
        Punishment punishment = punishable.getPunishment();
        if (punishment.getType() != Punishment.PunishmentType.MUTE) return;
        if (System.currentTimeMillis() >= punishment.getExpiresIn() && !punishment.isPermanent()) {
            punishable.setPunishment(null);
            return;
        }
        String message = "§cYou are muted " + (punishment.isPermanent() ? "§lpermanently!" : "for §l" + DateUtil.getDifferenceBetween(System.currentTimeMillis(), punishment.getExpiresIn()) + ".");
        player.sendMessage(message);
        event.setCancelled(true);
    }
}
