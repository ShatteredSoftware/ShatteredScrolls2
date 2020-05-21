package com.github.shatteredsuite.scrolls.extensions

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import java.util.*

fun Location.getNearestSafe(maxCount: Int = 100) : Location? {
    if(this.isSafe()) {
        return this
    }
    var count = 0
    val toVisit = LinkedList<Location>()
    while(toVisit.size > 0) {
        val next = toVisit.poll()
        for(bf in BlockFace.values()) {
            val loc = next.block.getRelative(bf).location
            if(loc.isSafe()) {
                return loc
            }
            toVisit.add(loc)
            count++
        }
        if(count >= maxCount) {
            return null
        }
    }
    return null
}

fun Location.isSafe() : Boolean {
    val block = this.block
    if (!block.isEmpty || !block.getRelative(BlockFace.UP).isEmpty) {
        return false
    }
    if (this.isDamaging()) {
        return false
    }
    if(this.isAboveAir()) {
        return false
    }
    return true
}

fun Location.isDamaging() : Boolean {
    val mat = this.world!!.getBlockAt(this).type
    return mat == Material.FIRE || mat == Material.LAVA || mat == Material.CAMPFIRE
}

fun Location.isAboveAir() : Boolean {
    return this.blockY > this.world!!.maxHeight || this.world!!.getBlockAt(this.add(0.0, -1.0, 0.0)).isEmpty
}