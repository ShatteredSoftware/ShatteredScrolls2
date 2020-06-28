package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditDisplayGlowCommand(val instance: ShatteredScrolls, parent: ScrollEditDisplayCommand) :
        LeafCommand(instance, parent, "glow", "shatteredscrolls.command.scroll.edit", "command.scroll.edit-display") {
    init {
        addAlias("g")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 3)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])
        val bindingType = BindingTypeValidator.validate(ctx.args[1])
        val allDisplays = HashMap(type.displays)
        val display = type.displays[bindingType.id]
        val glow = Validators.booleanValidator.validate(ctx.args[2])

        allDisplays[bindingType.id] = display!!.copy(glow = glow)
        val newType = type.copy(displays = allDisplays)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = glow.toString()
        ctx.contextMessages["display"] = bindingType.id
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.display.glow", ctx.contextMessages)
        ctx.contextMessages.putAll(newType.placeholders)
        ctx.sendMessage("edit-scroll", true)
    }
}