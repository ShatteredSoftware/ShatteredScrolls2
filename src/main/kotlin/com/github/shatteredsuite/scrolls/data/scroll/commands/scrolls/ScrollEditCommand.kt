package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.ParameterizedBranchCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.scrolls.ShatteredScrolls

class ScrollEditCommand(val instance: ShatteredScrolls, parent: ScrollCommand) :
        ParameterizedBranchCommand(instance, parent, "edit", "shatteredscrolls.command.scroll.edit", "command.scroll.edit") {
    init {
        registerSubcommand(ScrollEditIdCommand(instance, this))
        registerSubcommand(ScrollEditNameCommand(instance, this))
        registerSubcommand(ScrollEditDisplayCommand(instance, this))
        registerSubcommand(ScrollEditChargesCommand(instance, this))
        registerSubcommand(ScrollEditInfiniteCommand(instance, this))
        registerSubcommand(ScrollEditCostDataCommand(instance, this))
        registerSubcommand(ScrollEditBindingDataCommand(instance, this))
    }

    override fun provideCompletions(ctx: CommandContext?): List<String> {
        return instance.scrolls().all.map { it.id }
    }
}