package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.core.include.nbt.NBTItem
import com.github.shatteredsuite.scrolls.data.scroll.NBTApplier
import com.github.shatteredsuite.scrolls.data.scroll.ScrollInteractor

/**
 * Item-specific binding information, whether that's a warp, location, unbound, and contains all of
 * the information needed to handle an interaction.
 *
 * @see BindingType
 */
abstract class BindingData protected constructor(val type: String, val defaultDisplay: BindingDisplay) : ScrollInteractor, NBTApplier {
    override fun applyNBT(item: NBTItem): NBTItem {
        val compound = item!!.addCompound("binding")
        compound.setString("type", type)
        applyBindingNBT(compound)
        return item
    }

    /**
     * Applies binding-specific NBT to the item.
     * @param compound A compound given where all binding-specific NBT should be applied.
     */
    protected abstract fun applyBindingNBT(compound: NBTCompound)

    /**
     * Converts this BindingData into a map, for use in configs.
     *
     * @return The converted map.
     */
    abstract fun serialize(): Map<String?, Any?>

}