package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgMinPredicate
import com.github.shatteredsuite.core.commands.predicates.CancelResponse
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.predicates.PlayerPredicate
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.WarpValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpGotoCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance, parent, "goto", "shatteredscrolls.command.warp.goto", "command.warp.goto") {
    init {
        contextPredicates["player"] = PlayerPredicate()
        contextPredicates["args"] = ArgMinPredicate(CancelResponse(this.helpPath), 1)
    }

    override fun execute(ctx: CommandContext) {
        val warp = WarpValidator.validate(ctx.args[0])
        (ctx.sender as Player).teleport(warp.location)
        ctx.contextMessages.putAll(warp.placeholders)
        ctx.messenger.sendMessage(ctx.sender, "goto-warp", ctx.contextMessages, true)
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return TabCompleters.completeFromOptions(args, 0, instance.warps().all.map { it.id });
    }
}