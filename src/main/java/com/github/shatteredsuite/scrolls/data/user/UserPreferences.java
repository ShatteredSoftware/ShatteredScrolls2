package com.github.shatteredsuite.scrolls.data.user;

import java.util.UUID;

public class UserPreferences {
    public final UUID owner;
    public final int rounding;

    public UserPreferences(UUID owner, int rounding) {
        this.owner = owner;
        this.rounding = rounding;
    }
}
