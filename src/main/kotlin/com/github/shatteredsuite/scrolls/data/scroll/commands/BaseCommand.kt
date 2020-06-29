package com.github.shatteredsuite.scrolls.data.scroll.commands

import com.github.shatteredsuite.core.commands.BranchCommand
import com.github.shatteredsuite.core.commands.WrappedCommand
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.commands.scrolls.ScrollCommand
import com.github.shatteredsuite.scrolls.data.scroll.commands.warps.WarpCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class BaseCommand(instance: ShatteredScrolls) : BranchCommand(instance, null, "scrolls", "shatteredscrolls.command.base", "command.base") {
    init {
        val warp = WarpCommand(instance, this)
        this.registerSubcommand(warp)
        val scroll = ScrollCommand(instance, this)
        this.registerSubcommand(scroll)
        val load = LoadCommand(instance, this)
        this.registerSubcommand(load)
        val save = SaveCommand(instance, this)
        this.registerSubcommand(save)
        val version = VersionCommand(instance, this)
        this.registerSubcommand(version)
    }
}