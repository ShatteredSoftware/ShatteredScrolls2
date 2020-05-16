package com.github.shatteredsuite.scrolls.items;

import com.github.shatteredsuite.core.include.nbt.NBTItem;
import com.github.shatteredsuite.scrolls.ShatteredScrolls2;
import com.github.shatteredsuite.scrolls.data.scroll.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.LocationBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.UnboundBindingData;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public interface VersionConversion {
    ScrollInstance convert(ItemStack stack);
}
