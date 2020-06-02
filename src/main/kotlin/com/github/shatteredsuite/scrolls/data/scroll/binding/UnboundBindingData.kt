package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player
import java.util.*

class UnboundBindingData : BindingData("unbound", BindingDisplay("Unbound Scroll", false, LinkedList(), false, 0)) {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        return ScrollInstance(instance.scrollType, instance.charges, instance.isInfinite, LocationBindingData(player.location))
    }

    override fun applyBindingNBT(compound: NBTCompound) {
        // Intentionally blank; no data to be added.
    }

    override fun serialize(): Map<String?, Any?> {
        return LinkedHashMap()
    }
}