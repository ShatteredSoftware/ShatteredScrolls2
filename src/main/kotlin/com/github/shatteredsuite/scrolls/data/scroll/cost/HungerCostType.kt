package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player

class HungerCostType(val amount: Int) : CostType("hunger") {
    override fun deserialize(data: Any?): CostData {
        return HungerCostData(data as Int)
    }
}

class HungerCostData(private val amount: Int) : CostData() {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        player.foodLevel = player.foodLevel - amount
        return instance
    }
}