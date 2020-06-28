package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HealthCostType : CostType("health") {
    override fun deserialize(data: Any?): CostData {
        if(data == null) {
            return NoneCostData()
        }
        return HealthCostData(data as Int)
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): CostData {
        val amount = Validators.integerValidator.validate(args[0])
        return HealthCostData(amount)
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): List<String> {
        return TabCompleters.completeEvens(args, 0, 5)
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