package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.scrolls.items.NBTUtils;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.jetbrains.annotations.NotNull;

public class LocationBindingData extends BindingData implements ConfigurationSerializable {

    public final Location location;

    public LocationBindingData(Location location) {
        super("location");
        this.location = location;
    }

    @Override
    public ScrollInstance onInteract(ScrollInstance item, Player player) {
        PlayerTeleportEvent event = new PlayerTeleportEvent(player, player.getLocation(), location, TeleportCause.PLUGIN);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            return item;
        }
        return new ScrollInstance(item.scrollType, item.isInfinite ? item.charges : item.charges-1, item.isInfinite, item.bindingData);
    }

    @Override
    public void applyBindingNBT(NBTCompound compound) {
        NBTUtils.addLocationNBT(compound, this.location);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        return map;
    }
}
