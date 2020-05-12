package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.config.ConfigUtil;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class BindingData implements ConfigurationSerializable {

    public final BindingType bindingType;
    public final Location location;
    public final Warp warp;

    public BindingData(Location location) {
        this.bindingType = BindingType.LOCATION;
        this.location = location;
        this.warp = null;
    }

    public BindingData(Warp warp) {
        this.bindingType = BindingType.WARP;
        this.warp = warp;
        this.location = null;
    }

    public BindingData() {
        this.bindingType = BindingType.UNBOUND;
        this.warp = null;
        this.location = null;
    }

    public BindingData(BindingType bindingType, Location location, Warp warp) {
        this.bindingType = bindingType;
        this.location = location;
        this.warp = warp;
    }

    @Override
    public Map<String, Object> serialize() {
        return ConfigUtil.reflectiveSerialize(this, BindingData.class);
    }
}