package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.DefaultScrollConfig
import com.github.shatteredsuite.scrolls.validation.ScrollTypeValidator
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ScrollCreateCommand(val instance: ShatteredScrolls, parent: ScrollCommand) : LeafCommand(instance,
        parent, "delete", "shatteredscrolls.command.scroll.create", "command.scroll.create") {
    init {
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 1)
        addAlias("c")
    }

    override fun execute(context: CommandContext) {
        val scroll = context.args[0]
        val defaultScrollType = DefaultScrollConfig.config.scrollTypes[0]
        val newScrollType = defaultScrollType.copy(name = scroll)
        instance.scrolls().register(newScrollType)
        context.contextMessages.putAll(newScrollType.placeholders)
        context.messenger.sendMessage(context.sender, "create-scroll", context.contextMessages, true)
    }
}