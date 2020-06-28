package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator

class ScrollEditDisplayModelCommand(val instance: ShatteredScrolls, parent: ScrollEditDisplayCommand) :
        LeafCommand(instance, parent, "model", "shatteredscrolls.command.scroll.edit", "command.scroll.edit-display") {
    init {
        addAlias("m")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 3)
    }

    override fun execute(ctx: CommandContext) {
        val type = ScrollTypeValidator.validate(ctx.args[0])
        val bindingType = BindingTypeValidator.validate(ctx.args[1])
        val allDisplays = HashMap(type.displays)
        val display = type.displays[bindingType.id]
        val model = Validators.integerValidator.validate(ctx.args[2])

        allDisplays[bindingType.id] = display!!.copy(customModelData = model)
        val newType = type.copy(displays = allDisplays)

        instance.scrolls().delete(type.id)
        instance.scrolls().register(newType)

        ctx.contextMessages.putAll(newType.placeholders)
        ctx.contextMessages["value"] = model.toString()
        ctx.contextMessages["key"] = ctx.messenger.getMessage("scroll.display.model", ctx.contextMessages)
        ctx.sendMessage("edit-scroll", true)
    }
}