package com.github.shatteredsuite.scrolls.data.warp

import com.github.shatteredsuite.core.config.ConfigUtil
import com.github.shatteredsuite.scrolls.data.Identified
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable

class Warp : ConfigurationSerializable, Identified {
    override val id: String
    val name: String
    val external: Boolean
    val location: Location

    constructor(id: String, name: String, location: Location, external: Boolean = true) {
        this.id = id
        this.name = name
        this.location = location
        this.external = external
    }

    constructor(map: Map<String?, Any?>?) {
        id = ConfigUtil.getIfValid(map, "id", String::class.java, "")
        name = ConfigUtil.getIfValid(map, "name", String::class.java, "")
        this.external = false
        location = ConfigUtil.getIfValid(map, "location", Location::class.java, Location(null, 0.toDouble(), 0.toDouble(), 0.toDouble()))
    }

    override fun serialize(): Map<String, Any> {
        return ConfigUtil.reflectiveSerialize(this, Warp::class.java)
    }
}