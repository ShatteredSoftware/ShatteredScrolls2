package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.*
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingType
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingType
import com.github.shatteredsuite.scrolls.validation.BindingTypeValidator
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil

class ScrollGiveCommand(val instance: ShatteredScrolls, scrollCommand: ScrollCommand) : LeafCommand(instance, scrollCommand, "give", "shatteredscrolls.command.scroll.give", "command.scroll.give") {
    init {
        this.contextPredicates["args"] = ArgMinPredicate(CancelResponse(this.helpPath), 2)
    }

    override fun execute(ctx: CommandContext) {
        val target = Validators.playerValidator.validate(ctx.args[0])
        val scroll = ScrollTypeValidator.validate(ctx.args[1])
        var charges = scroll.defaultCharges
        var infinite = scroll.infinite

        if(ctx.args.size >= 3) {
            if(ctx.args[2] == "infinite") {
                charges = 0
                infinite = true
            }
            else {
                charges = ArgParser.validInt(ctx.args, 1)
            }
        }

        val count = if(ctx.args.size >= 4) ArgParser.validInt(ctx.args, 2) else 1

        val bindingType : BindingType = if (ctx.args.size >= 5) BindingTypeValidator.validate(ctx.args[3])
            else UnboundBindingType()

        val bindingData: BindingData = if(ctx.args.size >= 5)
            bindingType.createFromCommandArgs(ctx.args.sliceArray(5..ctx.args.lastIndex), ctx.sender)
            else UnboundBindingData()

        val chargesMsg = if (infinite) instance.messenger.getMessage("infinite", mapOf()) else charges.toString()

        instance.messenger.sendMessage(ctx.sender, "get-scroll", mapOf("count" to count.toString(),
                "scrollname" to scroll.name, "id" to scroll.id, "charges" to chargesMsg), true)

        val inst = scroll.createInstance(charges, infinite, bindingData)
        val stack = inst.toItemStack()
        stack.amount = count
        target.inventory.addItem(stack)
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        return if(!sender.hasPermission(this.permission)) {
            mutableListOf()
        }
        else if(args.size == 1) {
            TabCompleters.completeFromOptions(args, 0, Bukkit.getOnlinePlayers().map { it.name });
        }
        else if(args.size == 2) {
            TabCompleters.completeFromOptions(args, 1, instance.scrolls().all.map { it.id })
        }
        else if(args.size == 3) {
            val res = TabCompleters.completeOdds(args, 2, 3)
            res.add("infinite")
            val completions = mutableListOf<String>()
            StringUtil.copyPartialMatches(args[2], res, completions)
            completions
        }
        else if(args.size == 4) {
            TabCompleters.completeNumbers(args, 3, { it }, 1, 5 )
        }
        else if(args.size == 5) {
            TabCompleters.completeFromOptions(args, 4, instance.bindingTypes().all.map { it.id })
        }
        else if(args.size >= 6 && instance.bindingTypes()[args[4]] != null) {
            instance.bindingTypes()[args[4]].tabCompleteCommandArgs(args.sliceArray(5..args.lastIndex), sender)
        }
        else mutableListOf()
    }

    init {
        addAlias("gi")
    }
}