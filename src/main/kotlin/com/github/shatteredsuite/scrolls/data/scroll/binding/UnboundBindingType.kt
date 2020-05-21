package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound

class UnboundBindingType : BindingType("unbound") {
    override fun deserialize(map: Map<String?, Any?>): BindingData {
        return UnboundBindingData()
    }

    override fun fromNBT(compound: NBTCompound): BindingData {
        return UnboundBindingData()
    }
}