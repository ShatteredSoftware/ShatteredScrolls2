package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgMinPredicate
import com.github.shatteredsuite.core.commands.predicates.CancelResponse
import com.github.shatteredsuite.core.commands.predicates.CommandContext
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

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        val fixedArgs = StringUtil.fixArgs(args)
        if(fixedArgs.size <= 1) {
            return TabCompleters.completeFromOptions(fixedArgs, 0, instance.warps().all.map { it.id })
        }
        if(fixedArgs.size <= 2) {
            return TabCompleters.completeFromOptions(fixedArgs, 1, listOf("id", "name", "location"))
        }
        if(fixedArgs.size <= 3) {
            if(fixedArgs[2] == "location" && sender is Player) {
                return TabCompleters.completeLocationPlayer(fixedArgs, 2, sender)
            }
        }
        return emptyList()
    }
}