package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditNameCommand(val instance: ShatteredScrolls, parent: ScrollEditCommand) :
        LeafCommand(instance, parent, "name", "shatteredscrolls.command.scroll.edit", "command.scroll.edit") {
    init {
        addAlias("n")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])

        val rest = ctx.args.slice(1..ctx.args.lastIndex).joinToString(" ")
        if(rest.isBlank()) {
            ctx.sendErrorMessage("no-blank", true)
            return
        }
        val newType = type.copy(name = rest)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = rest
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.name", ctx.contextMessages)

        ctx.sendMessage("edit-scroll", true)
    }
}