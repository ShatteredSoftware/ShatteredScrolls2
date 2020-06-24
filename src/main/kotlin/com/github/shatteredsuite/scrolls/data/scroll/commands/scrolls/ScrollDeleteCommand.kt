package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgMinPredicate
import com.github.shatteredsuite.core.commands.predicates.CancelResponse
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator
import com.github.shatteredsuite.scrolls.validation.WarpValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ScrollDeleteCommand(val instance: ShatteredScrolls, parent: ScrollCommand) : LeafCommand(instance,
        parent, "delete", "shatteredscrolls.command.scroll.delete", "command.scroll.delete") {
    init {
        contextPredicates["args"] = ArgMinPredicate(CancelResponse(this.helpPath), 1)
        addAlias("d")
    }

    override fun execute(context: CommandContext) {
        val scroll = ScrollTypeValidator.validate(context.args[0])
        context.contextMessages.putAll(scroll.placeholders)
        if(scroll.id == instance.config().defaultTypeName) {
            context.messenger.sendMessage(context.sender, "no-delete-default", context.contextMessages, true)
        }
        instance.scrolls().delete(scroll.id)
        context.messenger.sendMessage(context.sender, "delete-scroll", context.contextMessages, true)
        return
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return TabCompleters.completeFromOptions(args, 0, instance.scrolls().all.map { it.id })
    }
}