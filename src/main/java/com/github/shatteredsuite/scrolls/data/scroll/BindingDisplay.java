package com.github.shatteredsuite.scrolls.data.scroll;


import static com.github.shatteredsuite.core.config.ConfigUtil.getIfValid;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("BindingDisplay")
public class BindingDisplay implements ConfigurationSerializable {
    public final String name;
    public final String lore;

    public BindingDisplay(String name, String lore) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.lore = ChatColor.translateAlternateColorCodes('&', lore);
    }

    public BindingDisplay(Map<String, Object> map) {
        this.name = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "name", String.class, "Unset Scroll Name"));
        this.lore = ChatColor.translateAlternateColorCodes('&',
            getIfValid(map, "lore", String.class, "Unset Scroll Lore"));
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("lore", lore);
        return map;
    }
}
