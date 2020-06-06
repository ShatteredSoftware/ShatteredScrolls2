package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player

class HealthCostType : CostType("health") {
    override fun deserialize(data: Any): CostData {
        return HealthCostData(data as Int)
    }
}

class HealthCostData(private val amount: Int) : CostData("health") {
    override fun serialize(): Any {
        return amount
    }

    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        player.health = player.health - amount
        return instance
    }
}