package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.validation.ChoiceValidator
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PotionCostType : CostType("potion") {
    override fun deserialize(data: Any?): CostData {
        if(data == null) {
            return NoneCostData()
        }
        return PotionCostData(data as PotionEffect)
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): CostData {
        val choice = PotionEffectType.getByName(potionChoices.validate(args[0]))!!
        val duration = Validators.integerValidator.validate(args[1])
        val amplifier = if(args.size >= 3) Validators.integerValidator.validate(args[2]) else 1
        val ambient = if(args.size >= 4) Validators.booleanValidator.validate(args[3]) else false
        val particles = if(args.size >= 5) Validators.booleanValidator.validate(args[4]) else true
        val icon = if (args.size == 6) Validators.booleanValidator.validate(args[5]) else true
        return PotionCostData(PotionEffect(choice, duration, amplifier, ambient, particles, icon))
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): List<String> {
        return when {
            args.size <= 1 -> {
                TabCompleters.completeFromOptions(args, 0, PotionEffectType.values().map { it.name })
            }
            args.size == 2 -> {
                TabCompleters.completeNumbers(args, 1, { it * 15 }, 1, 8)
            }
            args.size == 3 -> {
                TabCompleters.completeNumbers(args, 2, { it }, 1, 5)
            }
            args.size in 4..6 -> {
                TabCompleters.completeBoolean(args, args.lastIndex)
            }
            else -> emptyList()
        }
    }

    companion object {
        val potionChoices = ChoiceValidator(PotionEffectType.values().map { it.name })
    }
}

class PotionCostData(private val potion: PotionEffect) : CostData("potion") {
    override fun serialize(): Any {
        return potion
    }

    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        player.addPotionEffect(potion)
        return instance
    }
}