package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class UnboundBindingData extends BindingData {

    public UnboundBindingData() {
        super("location");
    }

    @Override
    public ScrollInstance onInteract(ScrollInstance instance, Player player) {
        return new ScrollInstance(instance.scrollType, instance.charges, instance.isInfinite, new LocationBindingData(player.getLocation()));
    }

    @Override
    protected void applyBindingNBT(NBTCompound compound) {
        // Intentionally blank; no data to be added.
    }

    @Override
    public Map<String, Object> serialize() {
        return new LinkedHashMap<>();
    }
}
