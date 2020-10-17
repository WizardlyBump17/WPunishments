package com.wizardlybump17.wpunishments.util;

import com.wizardlybump17.wpunishments.api.punishable.Punishment;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

public class MessageUtil {

    public static String getPunishmentMessage(@NonNull Punishment punishment, @NonNull PunishmentMessageType type) {
        switch (type) {
            case WHO_PUNISHED_APPLIED: {
                return StringUtils.join(new String[]{
                        "§aYou punished " + punishment.getPunished() + "!",
                        "§aDetails:",
                        "§2Punishment type: §f" + punishment.getType().name().toLowerCase(),
                        "§2Time: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(punishment.getAppliedIn(), punishment.getExpiresIn())),
                        "§2Reason: §f" + (punishment.getReason() == null ? "no reason given" : punishment.getReason())
                }, "\n");
            }
            case PUNISHED_APPLIED: {
                switch (punishment.getType()) {
                    case MUTE: {
                        return StringUtils.join(new String[]{
                                "§cYou've been muted by " + punishment.getWhoPunished() + "!",
                                "§2Details:",
                                "§2Time: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(punishment.getAppliedIn(), punishment.getExpiresIn())),
                                "§2Reason: §f" + (punishment.getReason() == null ? "no reason given" : punishment.getReason())
                        }, "\n");
                    }
                    case BAN: {
                        return StringUtils.join(new String[]{
                                "§cYou've been banned by " + punishment.getWhoPunished() + "!",
                                "§2Details:",
                                "§2Time: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(punishment.getAppliedIn(), punishment.getExpiresIn())),
                                "§2Reason: §f" + (punishment.getReason() == null ? "no reason given" : punishment.getReason())
                        }, "\n");
                    }
                }
            }
            case PUNISHED_ACTIVE_PUNISHMENT: {
                switch (punishment.getType()) {
                    case MUTE: {
                        return "§cYou are muted " + (punishment.isPermanent() ? "§lpermanently!" : "for §l" + DateUtil.getDifferenceBetween(System.currentTimeMillis(), punishment.getExpiresIn()));
                    }
                    case BAN: {
                        return StringUtils.join(new String[]{
                                "§cYou've been banned by " + punishment.getWhoPunished() + "!",
                                "§cDetails:",
                                "§2Time left: §f" + (punishment.isPermanent() ? "permanent" : DateUtil.getDifferenceBetween(System.currentTimeMillis(), punishment.getExpiresIn())),
                                "§2Reason: §f" + (punishment.getReason() == null ? "no reason given" : punishment.getReason())
                        }, "\n");
                    }
                }
            }
        }
        return "";
    }

    public enum PunishmentMessageType {
        WHO_PUNISHED_APPLIED, PUNISHED_APPLIED, PUNISHED_ACTIVE_PUNISHMENT
    }
}
