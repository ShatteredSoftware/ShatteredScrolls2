package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.include.nbt.NBTItem

/**
 * General functionality to apply NBT to an item.
 */
interface NBTApplier {
    /**
     * Given an item, applies NBT data to the item.
     * Should be the inverse of [NBTReader.fromNBT].
     *
     * @param item The item to apply NBT to.
     * @return The finished item.
     */
    fun applyNBT(item: NBTItem): NBTItem
}