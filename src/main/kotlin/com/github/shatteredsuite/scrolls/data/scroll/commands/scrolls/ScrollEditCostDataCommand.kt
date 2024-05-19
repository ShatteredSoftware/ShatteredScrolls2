package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.CostTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator
import kotlin.math.cos

class ScrollEditCostDataCommand(val instance: ShatteredScrolls, parent: ScrollEditCommand) :
        LeafCommand(instance, parent, "cost", "shatteredscrolls.command.scroll.edit", "command.scroll.edit") {
    init {
        addAlias("co")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])

        val data = try {
            val cost = CostTypeValidator.validate(ctx.args[1])
            cost.createFromCommandArgs(ctx.args.sliceArray(2..ctx.args.lastIndex), ctx.sender)
        } catch (e: IllegalArgumentException) {
            ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.cost", ctx.contextMessages)
            ctx.sendErrorMessage("invalid-cost", true)
            return
        }

        val newType = type.copy(cost = data)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = data.type
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.cost", ctx.contextMessages)

        ctx.sendMessage("edit-scroll", true)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return TabCompleters.completeFromOptions(ctx.args, 0, instance.costTypes().all.map { it.id })
        }
        if(ctx.args.size >= 2) {
            val cost = instance.costTypes().get(ctx.args[0])
            if(cost != null) {
                return cost.tabCompleteCommandArgs(ctx.args.sliceArray(1..ctx.args.lastIndex), ctx.sender)
            }
        }
        return emptyList()
    }
}