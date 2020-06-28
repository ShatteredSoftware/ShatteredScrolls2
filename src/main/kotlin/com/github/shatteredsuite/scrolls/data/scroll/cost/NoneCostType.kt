package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class NoneCostType : CostType("none") {
    override fun deserialize(data: Any?): CostData {
        return NoneCostData()
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): CostData {
        return NoneCostData()
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): List<String> {
        return emptyList()
    }
}

class NoneCostData : CostData("none") {
    override fun serialize(): Any {
        return HashMap<String, Any>()
    }

    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        return instance
    }
}
