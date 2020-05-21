package com.github.shatteredsuite.scrolls.data

import com.github.shatteredsuite.core.config.ConfigUtil
import com.github.shatteredsuite.core.util.StringUtil
import com.github.shatteredsuite.scrolls.ShatteredScrolls2
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData
import com.github.shatteredsuite.scrolls.scrolls
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable

class ScrollConfig(val defaultType: ScrollType, val safetyCheck: Boolean, val cooldown: Int) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return ConfigUtil.reflectiveSerialize(this, ScrollConfig::class.java)
    }

    companion object {
        fun deserialize(map: Map<String, Any>) : ScrollConfig {
            val defaultTypeName: String = ConfigUtil.getIfValid(map, "default-type", String::class.java, "")
            val defaultType = if(StringUtil.isEmptyOrNull(defaultTypeName) || ShatteredScrolls2.getInstance().scrolls[defaultTypeName] == null) {
                ScrollType("default", "Default Scroll", Material.PAPER, 0, false, UnboundBindingData(), HashMap(), ScrollCrafting(), NoneCostData(), false, 5);
            } else {
                ShatteredScrolls2.getInstance().scrolls[defaultTypeName]
            }
            val safetyCheck = ConfigUtil.getIfValid(map, "safety-check", Boolean::class.java, true)
            val cooldown = ConfigUtil.getIfValid(map, "cooldown", Int::class.java, 1000)
            return ScrollConfig(defaultType, safetyCheck, cooldown)
        }
    }
}