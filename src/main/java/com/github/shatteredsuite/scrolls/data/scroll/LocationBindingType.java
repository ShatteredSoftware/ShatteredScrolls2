package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import java.util.Map;
import org.bukkit.Location;

public class LocationBindingType extends BindingType {
    public LocationBindingType(Location location) {
        super("location");
    }

    @Override
    public BindingData fromNBT(NBTCompound compound) {
        return null;
    }

    @Override
    public BindingData deserialize(Map<String, Object> map) {
        return new LocationBindingData((Location) map.get("binding-data"));
    }
}
