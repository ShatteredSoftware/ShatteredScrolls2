package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgMinPredicate
import com.github.shatteredsuite.core.commands.predicates.CancelResponse
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.validation.ChoiceValidator
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.warp.Warp
import com.github.shatteredsuite.scrolls.extensions.locationFromCommandArgs
import com.github.shatteredsuite.scrolls.validation.WarpValidator

class WarpEditCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance, parent,
    "edit", "shatteredscrolls.command.warp.edit", "command.warp.edit") {
    init {
        addAlias("e")
        contextPredicates["args"] = ArgMinPredicate(CancelResponse(this.helpPath), 3)
    }

    override fun execute(ctx: CommandContext) {
        val warp = WarpValidator.validate(ctx.args[0])
        ctx.contextMessages.putAll(warp.placeholders)
        if(warp.external) {
            ctx.messenger.sendErrorMessage(ctx.sender, "external-warp-edit", ctx.contextMessages, true)
            return
        }

        val key = ChoiceValidator(listOf("id", "name", "location")).validate(ctx.args[1])
        val rest = ctx.args.slice(2..ctx.args.lastIndex)
        val newWarp = when (key) {
            "id" -> Warp(rest[0], warp.name, warp.location, warp.external)
            "name" -> Warp(warp.id, rest[0], warp.location, warp.external)
            else -> Warp(warp.id, warp.name, locationFromCommandArgs(ctx.args, ctx.sender), warp.external)
        }
        ctx.contextMessages["key"] = key
        ctx.contextMessages["value"] = rest.joinToString(" ")
        instance.warps().delete(warp.id)
        instance.warps().register(newWarp)
        ctx.messenger.sendMessage(ctx.sender, "edit-warp", ctx.contextMessages, true)
    }
}