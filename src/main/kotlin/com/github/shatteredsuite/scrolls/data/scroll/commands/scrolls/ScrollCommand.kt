package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.BranchCommand
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.commands.BaseCommand

class ScrollCommand(instance: ShatteredScrolls, baseCommand: BaseCommand) : BranchCommand(instance, baseCommand, "scroll", "shatteredscrolls.command.scroll", "command.scroll.base") {
    init {
        addAlias("s")
        registerSubcommand(ScrollGetCommand(instance, this))
        registerSubcommand(ScrollGiveCommand(instance, this))
        registerSubcommand(ScrollEditCommand(instance, this))
        registerSubcommand(ScrollDeleteCommand(instance, this))
        registerSubcommand(ScrollCreateCommand(instance, this))
    }
}