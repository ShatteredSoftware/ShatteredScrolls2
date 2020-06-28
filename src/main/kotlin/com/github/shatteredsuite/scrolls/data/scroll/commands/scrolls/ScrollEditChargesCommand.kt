package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditChargesCommand(val instance: ShatteredScrolls, parent: ScrollEditCommand) :
        LeafCommand(instance, parent, "charges", "shatteredscrolls.command.scroll.edit", "command.scroll.edit") {
    init {
        addAlias("c")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])
        val amount = Validators.integerValidator.validate(ctx.args[1])
        val newType = type.copy(defaultCharges = amount)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = amount.toString()
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.charges", ctx.contextMessages)

        ctx.sendMessage("edit-scroll", true)
    }

    override fun onTabComplete(ctx: CommandContext): MutableList<String> {
        return TabCompleters.completeOdds(ctx.args, 1, 3)
    }
}