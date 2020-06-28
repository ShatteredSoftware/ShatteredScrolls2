package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.*
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.WarpValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpGotoCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance, parent, "goto", "shatteredscrolls.command.warp.goto", "command.warp.goto") {
    init {
        contextPredicates["player"] = SenderPlayerPredicate()
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 1)
    }

    override fun execute(ctx: CommandContext) {
        val warp = WarpValidator.validate(ctx.args[0])
        (ctx.sender as Player).teleport(warp.location)
        ctx.contextMessages.putAll(warp.placeholders)
        ctx.messenger.sendMessage(ctx.sender, "goto-warp", ctx.contextMessages, true)
    }

    override fun onTabComplete(ctx: CommandContext): MutableList<String> {
        return TabCompleters.completeFromOptions(ctx.args, 0, instance.warps().ids.toList())
    }
}