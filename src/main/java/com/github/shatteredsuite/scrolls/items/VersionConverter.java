package com.github.shatteredsuite.scrolls.items;

import com.github.shatteredsuite.core.include.nbt.NBTItem;
import com.github.shatteredsuite.scrolls.ShatteredScrolls2;
import com.github.shatteredsuite.scrolls.data.scroll.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.LocationBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.UnboundBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.WarpBindingData;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class VersionConverter {
    public static ScrollInstance createScrollInstanceFromV1(ItemStack stack) {
        ScrollType newType = ShatteredScrolls2.getInstance().config().defaultType;
        NBTItem item = new NBTItem(stack);
        if(!item.hasKey("shatteredscrolls_bound")) {
            return null;
        }
        int charges = item.getInteger("shatteredscrolls_charges");
        boolean infinite = charges == -1024;
        BindingData bindingData;
        if(item.getByte("shatteredscrolls_bound") == 1) {
            if(item.hasKey("shatteredscrolls_destination")) {
                bindingData = new WarpBindingData(ShatteredScrolls2.getInstance().warps()
                    .get(item.getString("shatteredscrools_destination")));
            }
            else {
                Location location = NBTUtils.locationFromNBTItem(item, "shatteredscrolls_dest_");
                bindingData = new LocationBindingData(location);
            }
        } else {
            bindingData = new UnboundBindingData();
        }
        return new ScrollInstance(newType, charges, infinite, bindingData);
    }
}
