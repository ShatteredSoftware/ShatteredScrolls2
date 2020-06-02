package com.github.shatteredsuite.scrolls.data

import com.github.shatteredsuite.core.config.ConfigUtil
import com.github.shatteredsuite.core.util.StringUtil
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.Map

@SerializableAs("ScrollConfig")
class ScrollConfig(val defaultType: ScrollType, val safetyCheck: Boolean, val cooldown: Int) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return ConfigUtil.reflectiveSerialize(this, ScrollConfig::class.java)
    }

    override fun toString(): String {
        return "ScrollConfig(defaultType=$defaultType, safetyCheck=$safetyCheck, cooldown=$cooldown)"
    }

    companion object {
        fun deserialize(map: Map<String, Any>): ScrollConfig {
            if (map.containsKey("scroll-types")) {
                val list = map["scroll-types"] as ArrayList<*>
                JavaPlugin.getPlugin(ShatteredScrolls::class.java).logger.info("Loading ${list.size} types.")
                for (type in list) {
                    if (type is ScrollType) {
                        JavaPlugin.getPlugin(ShatteredScrolls::class.java).logger.info("Loading ScrollType ${type.id}")
                        JavaPlugin.getPlugin(ShatteredScrolls::class.java).scrolls().register(type.id, type)
                    }
                }
            } else {
                JavaPlugin.getPlugin(ShatteredScrolls::class.java).logger.info("Found no scroll types.")
            }
            val defaultTypeName: String = ConfigUtil.getIfValid(map, "default-type", String::class.java, "")
            val defaultType = if (StringUtil.isEmptyOrNull(defaultTypeName) || ShatteredScrolls.getInstance().scrolls()[defaultTypeName] == null) {
                ScrollType("default", "Default Scroll", Material.PAPER, 0, UnboundBindingData(), HashMap(), ScrollCrafting(), NoneCostData(), false, 5)
            } else {
                ShatteredScrolls.getInstance().scrolls()[defaultTypeName]
            }
            val safetyCheck = ConfigUtil.getIfValid(map, "safety-check", Boolean::class.java, true)
            val cooldown = ConfigUtil.getIfValid(map, "cooldown", Int::class.java, 1000)
            return ScrollConfig(defaultType, safetyCheck, cooldown)
        }
    }
}