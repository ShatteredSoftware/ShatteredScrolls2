package com.github.shatteredsuite.scrolls.extensions

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.LocationBindingData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

fun Location.getNearestSafe(maxCount: Int = 100): Location? {
    if (this.isSafe()) {
        return this
    }
    var count = 0
    val toVisit = LinkedList<Location>()
    while (toVisit.size > 0) {
        val next = toVisit.poll()
        for (bf in BlockFace.values()) {
            val loc = next.block.getRelative(bf).location
            if (loc.isSafe()) {
                return loc
            }
            toVisit.add(loc)
            count++
        }
        if (count >= maxCount) {
            return null
        }
    }
    return null
}

fun locationFromCommandArgs(args: Array<out String>, sender: CommandSender): Location {
    return if(sender is Player && args.isEmpty()) {
        sender.location
    }
    else if(sender is Player && args.size == 3) {
        ArgParser.validShortLocation(args, 0, sender)
    }
    else ArgParser.validLocation(args, 0)
}

fun Location.isSafe(): Boolean {
    val block = this.block
    if (!block.isEmpty || !block.getRelative(BlockFace.UP).isEmpty) {
        return false
    }
    if (this.isDamaging()) {
        return false
    }
    if (this.isAboveAir()) {
        return false
    }
    return true
}

fun Location.isDamaging(): Boolean {
    val mat = this.world!!.getBlockAt(this).type
    return mat == Material.FIRE || mat == Material.LAVA || mat == Material.CAMPFIRE
}

fun Location.isAboveAir(): Boolean {
    return this.blockY > this.world!!.maxHeight || this.world!!.getBlockAt(this.add(0.0, -1.0, 0.0)).isEmpty
}