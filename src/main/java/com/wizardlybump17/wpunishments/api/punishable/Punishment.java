package com.wizardlybump17.wpunishments.api.punishable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Punishment {

    @NonNull
    private final PunishmentType type;
    @NonNull
    private final String whoPunished;
    @Setter
    private long expiresIn;
    private final long appliedIn;
    private final boolean permanent;
    private final String reason;

    public Punishment(PunishmentType type, String whoPunished, long appliedIn) {
        this(type, whoPunished, 0, appliedIn, true, null);
    }

    public Punishment(PunishmentType type, String whoPunished, long expiresIn, long appliedIn) {
        this(type, whoPunished, expiresIn, appliedIn, expiresIn <= 0, null);
    }

    public Punishment(PunishmentType type, String whoPunished, long expiresIn, long appliedIn, String reason) {
        this(type, whoPunished, expiresIn, appliedIn, expiresIn <= 0, reason);
    }

    @AllArgsConstructor
    public enum PunishmentType {
        BAN,
        MUTE,
    }
}
