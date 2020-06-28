package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.items.NBTUtils
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class LocationBindingType : BindingType("location") {
    override fun fromNBT(compound: NBTCompound): BindingData {
        return LocationBindingData(NBTUtils.locationFromNBTCompound(compound, ""))
    }

    override fun deserialize(map: Map<String?, Any?>): BindingData {
        return LocationBindingData(map["binding-data"] as Location)
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): BindingData {
        return if(sender is Player && args.isEmpty()) {
            LocationBindingData(sender.location)
        }
        else if(sender is Player && args.size == 3) {
            LocationBindingData(ArgParser.validShortLocation(args, 0, sender))
        }
        else LocationBindingData(ArgParser.validLocation(args, 0))
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): List<String> {
        if(sender is Player) {
            return TabCompleters.completeLocationPlayer(args, 0, sender)
        }
        return mutableListOf()
    }
}