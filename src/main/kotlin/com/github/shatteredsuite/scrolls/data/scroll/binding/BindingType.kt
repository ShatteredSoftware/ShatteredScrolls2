package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.util.Identified
import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.scrolls.data.scroll.NBTReader
import org.bukkit.command.CommandSender

/**
 * A scroll-unspecific version of binding data that represents the high-level interaction of a scroll,
 * and provides the functionality to create scroll-specific versions.
 *
 * @see BindingData
 */
abstract class BindingType(override val id: String) : NBTReader, Identified {

    /**
     * Creates BindingData from a map, normally from a config.
     *
     * @param map The map to deserialize from.
     * @return The built BindingData.
     */
    abstract fun deserialize(map: Map<String?, Any?>): BindingData

    /**
     * @param args The command arguments, 0-indexed from the first important arg.
     * @param sender The sender trying to execute this command.
     * @throws ArgumentValidationException When the type cannot be constructed from the input.
     */
    @Throws(ArgumentValidationException::class)
    abstract fun createFromCommandArgs(args: Array<out String>, sender: CommandSender) : BindingData

    /**
     * @param args The current command arguments, 0-indexed from the first important arg.
     * @param sender The sender trying to tab complete this command.
     */
    abstract fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender) : List<String>
}