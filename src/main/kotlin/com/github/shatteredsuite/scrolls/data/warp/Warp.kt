package com.github.shatteredsuite.scrolls.data.warp

import com.github.shatteredsuite.core.util.Identified
import org.bukkit.Location

class Warp(override val id: String, val name: String, val location: Location, @Transient val external: Boolean = true) : Identified {
    val placeholders: Map<String, String>
        get() = mapOf("id" to id, "name" to name, "x" to location.x.toString(), "y" to location.y.toString(),
                "z" to location.z.toString(), "pitch" to location.pitch.toString(), "yaw" to location.yaw.toString(),
                "world" to location.world!!.name)
}
