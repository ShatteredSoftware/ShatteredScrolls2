package com.github.shatteredsuite.scrolls.data.scroll.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls

class LoadCommand(val instance: ShatteredScrolls, parent: BaseCommand) : LeafCommand(instance, parent, "load", "shatteredscrolls.load", "command.load") {
    override fun execute(ctx: CommandContext) {
        instance.readFromDisk()
        ctx.messenger.sendMessage(ctx.sender, "load", ctx.contextMessages, true)
    }
}