package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.ShatteredScrolls2
import com.github.shatteredsuite.scrolls.warps

class WarpBindingType : BindingType("warp") {
    override fun deserialize(map: Map<String?, Any?>): BindingData {
        val rawWarp: Any? = map["warp-id"]?: return UnboundBindingData()
        if(rawWarp !is String) {
            return UnboundBindingData()
        }
        return WarpBindingData(ShatteredScrolls2.getInstance().warps()[rawWarp])
    }

    override fun fromNBT(compound: NBTCompound): BindingData {
        val rawWarp: Any? = compound.getString("warp-id")?: return UnboundBindingData();
        if(rawWarp !is String) {
            return UnboundBindingData()
        }
        return WarpBindingData(ShatteredScrolls2.getInstance().warps[rawWarp])
    }
}
