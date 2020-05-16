package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.scrolls.data.scroll.BindingData;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class WarpBindingData extends BindingData {

    private final Warp warp;

    public WarpBindingData(Warp warp) {
        super("warp");
        this.warp = warp;
    }

    @Override
    protected void applyBindingNBT(NBTCompound compound) {

    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("warp-id", this.warp.id);
        return map;
    }

    @Override
    public ScrollInstance onInteract(ScrollInstance instance, Player player) {
        return null;
    }
}
