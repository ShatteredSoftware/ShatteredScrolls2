package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player

class XPCostType(val amount: Int) : CostType("xp") {
    override fun deserialize(data: Any?): CostData {
        return XPCostData(data as Int)
    }
}

class XPCostData(val amount: Int) : CostData() {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        player.exp = player.exp - amount
        return instance
    }
}