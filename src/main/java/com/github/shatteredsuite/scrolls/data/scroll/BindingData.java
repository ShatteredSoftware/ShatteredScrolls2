package com.github.shatteredsuite.scrolls.data.scroll;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.core.include.nbt.NBTItem;
import java.util.Map;

/**
 * Item-specific binding information, whether that's a warp, location, unbound, and contains all of
 * the information needed to handle an interaction.
 *
 * @see BindingType
 */
public abstract class BindingData implements ScrollInteractor, NBTApplier {

    public final String type;

    protected BindingData(String type) {
        this.type = type;
    }

    @Override
    public NBTItem applyNBT(NBTItem item) {
        NBTCompound compound = item.addCompound("binding");
        compound.setString("type", type);
        applyBindingNBT(compound);
        return item;
    }

    /**
     * Applies binding-specific NBT to the item.
     * @param compound A compound given where all binding-specific NBT should be applied.
     */
    protected abstract void applyBindingNBT(NBTCompound compound);

    /**
     * Converts this BindingData into a map, for use in configs.
     *
     * @return The converted map.
     */
    public abstract Map<String, Object> serialize();
}
