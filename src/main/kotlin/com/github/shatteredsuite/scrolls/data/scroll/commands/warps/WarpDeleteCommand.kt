package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgMinPredicate
import com.github.shatteredsuite.core.commands.predicates.CancelResponse
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.WarpValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class WarpDeleteCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance,
parent, "delete", "shatteredscrolls.command.warp.delete", "command.warp.delete") {
    init {
        contextPredicates["args"] = ArgMinPredicate(CancelResponse(this.helpPath), 1)
        addAlias("d")
    }

    override fun execute(context: CommandContext) {
        val warp = WarpValidator.validate(context.args[0])
        context.contextMessages.putAll(warp.placeholders)
        if(warp.external) {
            context.messenger.sendErrorMessage(context.sender, "external-warp-delete", context.contextMessages, true)
            return
        }
        instance.warps().delete(warp.id)
        context.messenger.sendMessage(context.sender, "delete-warp", context.contextMessages, true)
        return
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return TabCompleters.completeFromOptions(args, 0, instance.warps().all.filter { !it.external }.map { it.id })
    }
}