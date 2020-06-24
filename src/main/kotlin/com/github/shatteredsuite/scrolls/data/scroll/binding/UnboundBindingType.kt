package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import org.bukkit.command.CommandSender

class UnboundBindingType : BindingType("unbound") {
    override fun deserialize(map: Map<String?, Any?>): BindingData {
        return UnboundBindingData()
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): BindingData {
        return UnboundBindingData()
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): MutableList<String> {
        return mutableListOf()
    }

    override fun fromNBT(compound: NBTCompound): BindingData {
        return UnboundBindingData()
    }
}