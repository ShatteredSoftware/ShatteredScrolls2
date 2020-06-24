package com.github.shatteredsuite.scrolls.data.scroll.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls

class SaveCommand(val instance: ShatteredScrolls, parent: BaseCommand) : LeafCommand(instance, parent, "save", "shatteredscrolls.load", "command.load") {
    override fun execute(ctx: CommandContext) {
        instance.saveContent()
        ctx.messenger.sendMessage(ctx.sender, "save", ctx.contextMessages, true)
    }
}
