package com.github.shatteredsuite.scrolls.data.scroll.commands.warps

import com.github.shatteredsuite.core.commands.BranchCommand
import com.github.shatteredsuite.core.commands.WrappedCommand
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.commands.BaseCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.util.StringUtil
import java.util.*

class WarpCommand(instance: ShatteredScrolls, parent: BaseCommand?) : BranchCommand(instance, parent, "warp", "shatteredscrolls.command.warp", "command.warp.base")  {
    init {
        addAlias("w")
        registerSubcommand(WarpGotoCommand(instance, this))
        registerSubcommand(WarpCreateCommand(instance, this))
    }
}