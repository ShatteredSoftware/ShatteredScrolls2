package com.github.shatteredsuite.scrolls.config;

import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import java.util.ArrayList;

public class ScrollConfigDTO {
    private String defaultType;
    private boolean teleportSafety = false;
    private int cooldown = 1000;

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public boolean isTeleportSafety() {
        return teleportSafety;
    }

    public void setTeleportSafety(boolean teleportSafety) {
        this.teleportSafety = teleportSafety;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public ArrayList<ScrollType> getScrollTypes() {
        return scrollTypes;
    }

    public void setScrollTypes(
        ArrayList<ScrollType> scrollTypes) {
        this.scrollTypes = scrollTypes;
    }

    public void addScrollType(ScrollType scrollType) {
        this.scrollTypes.add(scrollType);
    }

    private ArrayList<ScrollType> scrollTypes;
}
