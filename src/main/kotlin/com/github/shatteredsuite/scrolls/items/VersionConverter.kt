package com.github.shatteredsuite.scrolls.items

import com.github.shatteredsuite.core.include.nbt.NBTItem
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.LocationBindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.WarpBindingData
import org.bukkit.inventory.ItemStack

object VersionConverter {
    @JvmStatic
    fun createScrollInstanceFromV1(stack: ItemStack): ScrollInstance? {
        val inst = ShatteredScrolls.getInstance()
        val newType = inst.config().getDefaultType()
        val item = NBTItem(stack)
        if (!item.hasKey("shatteredscrolls_bound")) {
            return null
        }
        val charges = item.getInteger("shatteredscrolls_charges")
        val infinite = charges == -1024
        val bindingData: BindingData
        bindingData = if (item.getByte("shatteredscrolls_bound").toInt() == 1) {
            if (item.hasKey("shatteredscrolls_destination")) {
                WarpBindingData(inst.warps()[item.getString("shatteredscrolls_destination")])
            } else {
                val location = NBTUtils.locationFromNBTItem(item, "shatteredscrolls_dest_")
                LocationBindingData(location)
            }
        } else {
            UnboundBindingData()
        }
        return ScrollInstance(newType, charges, infinite, bindingData)
    }
}