package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player

class NoneCostType : CostType("none") {
    override fun deserialize(data: Any?): CostData {
        return NoneCostData()
    }
}

class NoneCostData : CostData() {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        return instance;
    }
}
