package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.core.include.nbt.NBTItem;

/**
 * General functionality to create BindingData from NBT.
 */
public interface NBTReader {

    /**
     * Creates BindingData from an NBT item.
     * The inverse of {@link NBTApplier#applyNBT(NBTItem)}.
     *
     * @param compound The compound to read data from to create BindingData.
     * @return The BindingData created from the item.
     */
    BindingData fromNBT(NBTCompound compound);
}
