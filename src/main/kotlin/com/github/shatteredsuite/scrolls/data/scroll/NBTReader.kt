package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData

/**
 * General functionality to create BindingData from NBT.
 */
interface NBTReader {
    /**
     * Creates BindingData from an NBT item.
     * The inverse of [NBTApplier.applyNBT].
     *
     * @param compound The compound to read data from to create BindingData.
     * @return The BindingData created from the item.
     */
    fun fromNBT(compound: NBTCompound): BindingData
}