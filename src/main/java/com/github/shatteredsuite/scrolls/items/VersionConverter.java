package com.github.shatteredsuite.scrolls.items;

import com.github.shatteredsuite.core.include.nbt.NBTItem;
import com.github.shatteredsuite.scrolls.ShatteredScrolls2;
import com.github.shatteredsuite.scrolls.data.scroll.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class VersionConverter {

    public static ScrollInstance v1_v2(ItemStack stack) {

        ScrollType newType = ShatteredScrolls2.getInstance().config().defaultType;
        NBTItem item = new NBTItem(stack);
        if(!item.hasKey("shatteredscrolls_bound")) {
            return null;
        }
        int charges = item.getInteger("shatteredscrolls_charges");
        boolean infinite = charges == -1024;
        BindingData bindingData = new BindingData();
        if(item.getByte("shatteredscrolls_bound") == 1) {
            if(item.hasKey("shatteredscrolls_destination")) {
                bindingData = new BindingData(ShatteredScrolls2.getInstance().warps()
                    .get(item.getString("shatteredscrolls_destination")));
            }
            else {
                Location location = NBTUtils.locationFromNBTItem(item, "shatteredscrolls_dest_");
                bindingData = new BindingData(location);
            }
        }
        return new ScrollInstance(newType, charges, infinite, bindingData);
    }
}
