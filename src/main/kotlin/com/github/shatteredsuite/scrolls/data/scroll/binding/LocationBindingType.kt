package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.items.NBTUtils
import org.bukkit.Location

class LocationBindingType() : BindingType("location") {
    override fun fromNBT(compound: NBTCompound): BindingData {
        return LocationBindingData(NBTUtils.locationFromNBTCompound(compound, ""))
    }

    override fun deserialize(map: Map<String?, Any?>): BindingData {
        return LocationBindingData(map["binding-data"] as Location)
    }
}