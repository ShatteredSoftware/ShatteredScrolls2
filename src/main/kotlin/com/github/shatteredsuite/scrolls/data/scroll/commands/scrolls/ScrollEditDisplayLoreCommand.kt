package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditDisplayLoreCommand(val instance: ShatteredScrolls, parent: ScrollEditDisplayCommand) :
        LeafCommand(instance, parent, "lore", "shatteredscrolls.command.scroll.edit", "command.scroll.edit-display") {
    init {
        addAlias("l")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 3)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])
        val bindingType = BindingTypeValidator.validate(ctx.args[1])
        val allDisplays = HashMap(type.displays)
        val display = type.displays[bindingType.id]
        val rest = ctx.args.slice(2..ctx.args.lastIndex).joinToString(" ")

        if(rest.isBlank()) {
            ctx.contextMessages.putAll(type.placeholders)
            ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.display.lore", ctx.contextMessages)
            ctx.sendErrorMessage("no-blank", true)
            return
        }

        val lines = rest.split(pattern)
        allDisplays[bindingType.id] = display!!.copy(rawLore = lines.toMutableList())
        val newType = type.copy(displays = allDisplays)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = lines.joinToString { "${it}\n" }
        ctx.contextMessages["display"] = bindingType.id
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.display.lore", ctx.contextMessages)
        ctx.sendMessage("edit-scroll", true)
    }

    companion object {
        val pattern = Regex("(?<!(\\+))\\\\n")
    }
}