package com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls

import com.github.shatteredsuite.core.commands.BranchCommand
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.commands.BaseCommand

class ScrollCommand(instance: ShatteredScrolls, baseCommand: BaseCommand) : BranchCommand(instance, baseCommand, "scroll", "shatteredscrolls.command.scroll", "command.scroll.base") {
    init {
        addAlias("s")
        this.registerSubcommand(ScrollGetCommand(instance, this))
        this.registerSubcommand(ScrollGiveCommand(instance, this))
    }
}