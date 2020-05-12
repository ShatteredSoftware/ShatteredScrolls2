package com.github.shatteredsuite.scrolls.data.warp;

import static com.github.shatteredsuite.core.config.ConfigUtil.getIfValid;

import com.github.shatteredsuite.core.config.ConfigUtil;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Warp implements ConfigurationSerializable {
    public final String id;
    public final String name;
    public final Location location;

    public Warp(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Warp(Map<String, Object> map) {
        this.id = getIfValid(map, "id", String.class, "");
        this.name = getIfValid(map, "name", String.class, "");
        this.location = getIfValid(map, "location", Location.class, new Location(null, 0, 0, 0));
    }


    @Override
    public Map<String, Object> serialize() {
        return ConfigUtil.reflectiveSerialize(this, Warp.class);
    }
}
