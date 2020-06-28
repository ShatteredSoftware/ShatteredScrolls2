package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditBindingDataCommand(val instance: ShatteredScrolls, parent: ScrollEditCommand) :
        LeafCommand(instance, parent, "binding", "shatteredscrolls.command.scroll.edit", "command.scroll.edit") {
    init {
        addAlias("b")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])

        val binding = BindingTypeValidator.validate(ctx.args[1])
        val data = binding.createFromCommandArgs(ctx.args.sliceArray(2..ctx.args.lastIndex), ctx.sender)
        val newType = type.copy(bindingData = data)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = data.type
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.binding", ctx.contextMessages)

        ctx.sendMessage("edit-scroll", true)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return TabCompleters.completeFromOptions(ctx.args, 0, instance.bindingTypes().all.map { it.id })
        }
        if(ctx.args.size >= 2) {
            val binding = instance.bindingTypes().get(ctx.args[0])
            if(binding != null) {
                return binding.tabCompleteCommandArgs(ctx.args.sliceArray(1..ctx.args.lastIndex), ctx.sender)
            }
        }
        return emptyList()
    }
}