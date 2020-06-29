package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls

class WarpConvertCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance, parent, "convert", "shatteredscrolls.command.warp.convert", "commands.warp.convert") {
    init {
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val externalWarp = instance.warps()[ctx.args[0]]
        val newId = ctx.args[1]
        ctx.contextMessages.putAll(externalWarp.placeholders)
        ctx.contextMessages["newid"] = newId
        val newWarp = externalWarp.copy(id = newId, external = false)
        instance.warps().register(newWarp)
        ctx.sendMessage("convert-warp", true)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return instance.warps().all.filter{ it.external }.map { it.id }
        }
        return emptyList()
    }
}