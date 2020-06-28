package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HungerCostType : CostType("hunger") {
    override fun deserialize(data: Any?): CostData {
        if(data == null) {
            return NoneCostData()
        }
        return HungerCostData(data as Int)
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): CostData {
        val amount = Validators.integerValidator.validate(args[0])
        return HungerCostData(amount)
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): List<String> {
        return TabCompleters.completeEvens(args, 0, 5)
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