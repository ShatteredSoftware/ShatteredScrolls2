package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.config.ConfigUtil
import com.github.shatteredsuite.core.util.Identified
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostData
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("ScrollType")
class ScrollType(override val id: String, val name: String, val material: Material, val customModelData: Int, val bindingData: BindingData,
                 val displays: HashMap<String, BindingDisplay>, val crafting: ScrollCrafting, val cost: CostData,
                 val infinite: Boolean, val defaultCharges: Int) : ConfigurationSerializable, Identified {
    override fun serialize(): Map<String, Any> {
        return ConfigUtil.reflectiveSerialize(this, ScrollType::class.java)
    }

    fun createInstance(): ScrollInstance {
        return ScrollInstance(this, defaultCharges, infinite, bindingData)
    }

    fun createInstance(charges: Int, infinite: Boolean, bindingData: BindingData): ScrollInstance {
        return ScrollInstance(this, charges, infinite, bindingData)
    }
}