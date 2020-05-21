package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect

class PotionCostType() : CostType("potion") {
    override fun deserialize(data: Any?): CostData {
        if(data != null) {
            return PotionCostData(data as PotionEffect)
        }
        return NoneCostData()
    }
}

class PotionCostData(val potion: PotionEffect) : CostData() {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        player.addPotionEffect(potion)
        return instance
    }
}