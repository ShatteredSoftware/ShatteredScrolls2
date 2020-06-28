package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.ParameterizedBranchCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls

class ScrollEditDisplayCommand(val instance: ShatteredScrolls, parent: ScrollEditCommand) :
    ParameterizedBranchCommand(instance, parent, "display", "shatteredscrolls.command.scroll.edit",
        "command.scroll.edit-display", 2) {
    init {
        addAlias("d")
        registerSubcommand(ScrollEditDisplayLoreCommand(instance, this))
        registerSubcommand(ScrollEditDisplayNameCommand(instance, this))
        registerSubcommand(ScrollEditDisplayModelCommand(instance, this))
        registerSubcommand(ScrollEditDisplayGlowCommand(instance, this))
    }

    override fun execute(ctx: CommandContext) {
        ctx.sender.sendMessage(ctx.args.joinToString())
        super.execute(ctx)
    }

    override fun provideCompletions(ctx: CommandContext?): List<String> {
        return instance.bindingTypes().all.map { it.id }
    }
}