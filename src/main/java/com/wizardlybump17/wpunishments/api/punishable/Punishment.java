package com.wizardlybump17.wpunishments.api.punishable;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
public class Punishment {

    @NonNull
    private final PunishmentType type;
    @NonNull
    private final String whoPunished;
    @NonNull
    private final String punished;
    @Setter
    private long duration;
    private final long appliedIn;
    @Setter
    private boolean permanent;
    private final String reason;

    public long getExpiresIn() {
        return appliedIn + duration;
    }

    @AllArgsConstructor
    public enum PunishmentType {
        BAN,
        MUTE,
    }
}
