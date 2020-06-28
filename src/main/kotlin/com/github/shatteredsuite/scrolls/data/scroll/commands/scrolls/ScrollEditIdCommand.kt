package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditIdCommand(val instance: ShatteredScrolls, parent: ScrollEditCommand) :
        LeafCommand(instance, parent, "id", "shatteredscrolls.command.scroll.edit", "command.scroll.edit") {
    init {
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])
        val rest = ctx.args[1]

        if(rest.isBlank()) {
            ctx.contextMessages.putAll(type.placeholders)
            ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.display.id", ctx.contextMessages)
            ctx.sendErrorMessage("no-blank", true)
            return
        }
        val newType = type.copy(id = rest)

        if(newType.id == instance.config().defaultTypeName) {
            instance.config().defaultTypeName = newType.id
        }

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = rest
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.id", ctx.contextMessages)

        ctx.sendMessage("edit-scroll", true)
    }
}