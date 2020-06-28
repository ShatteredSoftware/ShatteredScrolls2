package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.core.util.Identified
import com.github.shatteredsuite.core.validation.ArgumentValidationException
import org.bukkit.command.CommandSender

abstract class CostType(override var id: String) : Identified {
    abstract fun deserialize(data: Any?): CostData
    /**
     * @param args The command arguments, 0-indexed from the first important arg.
     * @param sender The sender trying to execute this command.
     * @throws ArgumentValidationException When the type cannot be constructed from the input.
     */
    @Throws(ArgumentValidationException::class)
    abstract fun createFromCommandArgs(args: Array<out String>, sender: CommandSender) : CostData

    /**
     * @param args The current command arguments, 0-indexed from the first important arg.
     * @param sender The sender trying to tab complete this command.
     */
    abstract fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender) : List<String>
}