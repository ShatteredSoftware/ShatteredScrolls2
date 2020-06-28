package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.*
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingType
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingType
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class ScrollGetCommand(val instance: ShatteredScrolls, scrollCommand: ScrollCommand) : LeafCommand(instance, scrollCommand, "get", "shatteredscrolls.command.scroll.get", "command.scroll.get") {
    init {
        this.contextPredicates["player"] = SenderPlayerPredicate()
        this.contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 1)
    }

    override fun execute(ctx: CommandContext) {
        val scroll = ScrollTypeValidator.validate(ctx.args[0])
        var charges = scroll.defaultCharges
        var infinite = scroll.infinite

        if(ctx.args.size >= 2) {
            if(ctx.args[1] == "infinite") {
                charges = 0
                infinite = true
            }
            else {
                charges = ArgParser.validInt(ctx.args, 1)
            }
        }

        val count = if(ctx.args.size >= 3) ArgParser.validInt(ctx.args, 2) else 1

        val bindingType : BindingType = if (ctx.args.size >= 4) BindingTypeValidator.validate(ctx.args[3])
            else UnboundBindingType()

        val bindingData: BindingData = if(ctx.args.size >= 4)
            bindingType.createFromCommandArgs(ctx.args.sliceArray(4..ctx.args.lastIndex), ctx.sender)
            else scroll.bindingData

        val chargesMsg = if (infinite) instance.messenger.getMessage("infinite", mapOf()) else charges.toString()

        instance.messenger.sendMessage(ctx.sender, "get-scroll", mapOf("count" to count.toString(),
                "scrollname" to scroll.name, "id" to scroll.id, "charges" to chargesMsg), true)

        val inst = scroll.createInstance(charges, infinite, bindingData)
        val stack = inst.toItemStack()
        stack.amount = count
        (ctx.sender as Player).inventory.addItem(stack)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        return if(!ctx.sender.hasPermission(this.permission)) {
            mutableListOf()
        }
        else if(ctx.args.size == 1) {
            TabCompleters.completeFromOptions(ctx.args, 0, instance.scrolls().all.map { it.id })
        }
        else if(ctx.args.size == 2) {
            val res = TabCompleters.completeOdds(ctx.args, 1, 3)
            res.add("infinite")
            val completions = mutableListOf<String>()
            StringUtil.copyPartialMatches(ctx.args[1], res, completions)
            completions
        }
        else if(ctx.args.size == 3) {
            TabCompleters.completeNumbers(ctx.args, 2, { it }, 1, 5 )
        }
        else if(ctx.args.size == 4) {
            TabCompleters.completeFromOptions(ctx.args, 3, instance.bindingTypes().all.map { it.id })
        }
        else if(ctx.args.size >= 5 && instance.bindingTypes()[ctx.args[3]] != null) {
            instance.bindingTypes()[ctx.args[3]].tabCompleteCommandArgs(ctx.args.sliceArray(4..ctx.args.lastIndex), ctx.sender)
        }
        else mutableListOf()
    }

    init {
        addAlias("g")
    }
}