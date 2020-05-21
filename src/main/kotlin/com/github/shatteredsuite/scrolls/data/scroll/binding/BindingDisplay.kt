package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.config.ConfigUtil
import org.bukkit.ChatColor
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*
import java.util.stream.Collectors

@SerializableAs("BindingDisplay")
class BindingDisplay(name: String?, preserveName: Boolean, lore: List<String?>) : ConfigurationSerializable {
    val name: String = ChatColor.translateAlternateColorCodes('&', name!!)
    var preserveName: Boolean = preserveName
        private set

    val lore: MutableList<String> = lore.stream().map {
        ChatColor.translateAlternateColorCodes('&', it!!)
    }.collect(Collectors.toList())

    override fun serialize(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map["name"] = name
        map["lore"] = lore
        map["preserve-name"] = preserveName
        return map
    }

    companion object {
        fun deserialize(map: Map<String?, Any?>?): BindingDisplay {
            val name = ChatColor.translateAlternateColorCodes('&',
                    ConfigUtil.getIfValid(map, "name", String::class.java, "Unset Scroll Name"))
            val lore: List<String?> = ConfigUtil.getIfValid(map, "lore", MutableList::class.java, LinkedList<String>()) as List<String?>
            val preserveName = ConfigUtil.getIfValid(map, "preserve-name", Boolean::class.java, false)
            return BindingDisplay(name, preserveName, lore)
        }
    }

}