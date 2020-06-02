package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.include.nbt.NBTCompound

/**
 * General functionality to apply NBT to an item.
 */
interface NBTApplier {
    /**
     * Given an item, applies NBT data to the item.
     * Should be the inverse of [NBTReader.fromNBT].
     *
     * @param compound The item to apply NBT to.
     * @return The finished item.
     */
    fun applyNBT(compound: NBTCompound): NBTCompound
}