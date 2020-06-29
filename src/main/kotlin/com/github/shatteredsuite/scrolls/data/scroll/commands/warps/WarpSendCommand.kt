package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.*
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.WarpValidator
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WarpSendCommand(val instance: ShatteredScrolls, parent: WarpCommand) : LeafCommand(instance, parent, "send", "shatteredscrolls.command.warp.send", "command.warp.send") {
    init {
        addAlias("s")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val target = Validators.playerValidator.validate(ctx.args[0])
        val warp = WarpValidator.validate(ctx.args[1])
        target.teleport(warp.location)
        ctx.contextMessages.putAll(warp.placeholders)
        ctx.contextMessages["target"] = target.name
        ctx.messenger.sendMessage(ctx.sender, "send-warp", ctx.contextMessages, true)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return TabCompleters.completeFromOptions(ctx.args, 0, Bukkit.getOnlinePlayers().map { it.name })
        }
        if(ctx.args.size == 2) {
            return TabCompleters.completeFromOptions(ctx.args, 1, instance.warps().ids.toList())
        }
        return emptyList()
    }
}