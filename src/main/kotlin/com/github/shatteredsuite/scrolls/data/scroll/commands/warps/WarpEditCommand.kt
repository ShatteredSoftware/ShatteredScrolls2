package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.util.StringUtil
import com.github.shatteredsuite.core.validation.ChoiceValidator
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.warp.Warp
import com.github.shatteredsuite.scrolls.extensions.locationFromCommandArgs
import com.github.shatteredsuite.scrolls.validation.WarpValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpEditCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance, parent,
    "edit", "shatteredscrolls.command.warp.edit", "command.warp.edit") {
    init {
        addAlias("e")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
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
        if(rest.isEmpty() && key != "location") {
            ctx.contextMessages["argc"] = ctx.args.size.toString()
            ctx.contextMessages["argx"] = "3"
            ctx.messenger.sendErrorMessage(ctx.sender, "not-enough-args", ctx.contextMessages, true)
        }
        val newWarp = when (key) {
            "id" -> Warp(rest[0], warp.name, warp.location, warp.external)
            "name" -> Warp(warp.id, rest[0], warp.location, warp.external)
            else -> Warp(warp.id, warp.name, locationFromCommandArgs(rest.toTypedArray(), ctx.sender), warp.external)
        }
        ctx.contextMessages["key"] = key
        ctx.contextMessages["value"] = if(key == "location") rest.joinToString(" ") else rest[0]
        instance.warps().delete(warp.id)
        instance.warps().register(newWarp)
        ctx.messenger.sendMessage(ctx.sender, "edit-warp", ctx.contextMessages, true)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return TabCompleters.completeFromOptions(ctx.args, 0, instance.warps().all.filter{ !it.external }.map { it.id })
        }
        if(ctx.args.size == 2) {
            return TabCompleters.completeFromOptions(ctx.args, 1, listOf("id", "name", "location"))
        }
        if(ctx.args.size >= 3) {
            if(ctx.args[1] == "location" && ctx.sender is Player) {
                return TabCompleters.completeLocationPlayer(ctx.args.sliceArray(2..ctx.args.lastIndex), 0, ctx.sender as Player)
            }
        }
        return emptyList()
    }
}