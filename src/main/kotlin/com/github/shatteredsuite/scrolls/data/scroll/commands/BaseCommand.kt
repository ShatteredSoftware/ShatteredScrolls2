package com.github.shatteredsuite.scrolls.data.scroll.commands

import com.github.shatteredsuite.core.commands.WrappedCommand
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class BaseCommand(private val instance: ShatteredScrolls) : WrappedCommand(instance, null, "scrolls", "shatteredscrolls.command.base", "command.base") {
    override fun onCommand(commandSender: CommandSender, command: Command, label: String,
                           args: Array<String>): Boolean {
        if (!showHelpOrNoPerms(commandSender, label, args)) {
            return true
        }
        instance.messenger.sendMessage(commandSender, "command.base", true)
        return true
    }

}