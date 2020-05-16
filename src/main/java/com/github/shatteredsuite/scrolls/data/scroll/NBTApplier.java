package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTItem;

/**
 * General functionality to apply NBT to an item.
 */
public interface NBTApplier {

    /**
     * Given an item, applies NBT data to the item.
     * Should be the inverse of {@link NBTReader#fromNBT(NBTItem)}.
     *
     * @param item The item to apply NBT to.
     * @return The finished item.
     */
    NBTItem applyNBT(NBTItem item);
}
