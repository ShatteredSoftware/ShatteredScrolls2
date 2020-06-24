package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player

class HungerCostType : CostType("hunger") {
    override fun deserialize(data: Any?): CostData {
        if(data == null) {
            return NoneCostData()
        }
        return HungerCostData(data as Int)
    }
}

class HungerCostData(private val amount: Int) : CostData("hunger") {
    override fun serialize(): Any {
        return amount
    }

    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        player.foodLevel = player.foodLevel - amount
        return instance
    }
}