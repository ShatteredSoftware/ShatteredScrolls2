package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.config.ConfigException
import com.github.shatteredsuite.core.config.ConfigUtil
import com.github.shatteredsuite.core.include.xseries.XMaterial
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostData
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostType
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostType
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*
import kotlin.collections.LinkedHashMap

@SerializableAs("ScrollType")
class ScrollType(val id: String, val name: String, val material: Material, val customModelData: Int, val bindingData: BindingData,
                 val displays: HashMap<String, BindingDisplay>, val crafting: ScrollCrafting, val cost: CostData,
                 val infinite: Boolean, val defaultCharges: Int) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return ConfigUtil.reflectiveSerialize(this, ScrollType::class.java)
    }

    fun createInstance(): ScrollInstance {
        return ScrollInstance(this, defaultCharges, infinite, bindingData)
    }

    class Builder {
        var id: String? = null
        var name: String? = "Teleportation Scroll"
        var material: Material? = Material.PAPER
        var customModelData = 0
        var glow = false
        var crafting: ScrollCrafting? = ScrollCrafting()
        var cost: CostData? = NoneCostData()
        var infinite = false
        var defaultCharges = 0
        var bindingData: BindingData? = UnboundBindingData()
        var displays: HashMap<String, BindingDisplay>? = HashMap()
        fun id(id: String?): Builder {
            this.id = id
            return this
        }

        fun name(name: String): Builder {
            this.name = name
            return this
        }

        fun material(material: Material): Builder {
            this.material = material
            return this
        }

        fun customModelData(customModelData: Int): Builder {
            this.customModelData = customModelData
            return this
        }

        fun glow(glow: Boolean): Builder {
            this.glow = glow
            return this
        }

        fun crafting(crafting: ScrollCrafting): Builder {
            this.crafting = crafting
            return this
        }

        fun cost(cost: CostData): Builder {
            this.cost = cost
            return this
        }

        fun infinite(infinite: Boolean): Builder {
            this.infinite = infinite
            return this
        }

        fun defaultCharges(defaultCharges: Int): Builder {
            this.defaultCharges = defaultCharges
            return this
        }

        fun bindingData(bindingData: BindingData): Builder {
            this.bindingData = bindingData
            return this
        }

        fun build(): ScrollType {
            return ScrollType(id!!, name!!, material!!, customModelData, bindingData!!, displays!!, crafting!!, cost!!, infinite, defaultCharges)
        }

        fun displays(displays: HashMap<String, BindingDisplay>): Builder {
            this.displays = displays
            return this
        }
    }

    companion object {
        @Throws(ConfigException::class)
        fun deserialize(map: Map<String?, Any?>): ScrollType {
            val builder = Builder()
            val id = ConfigUtil.getIfValid(map, "id", String::class.java, null)
                    ?: throw ConfigException("ScrollType id is required.")
            builder.id(id)

            val name = ConfigUtil.getIfValid(map, "name", String::class.java, null)
                    ?: throw ConfigException("ScrollType id is required.")
            builder.name(name)

            val material = ConfigUtil.getMaterialOrDef(map, "material",
                    Objects.requireNonNull(XMaterial.PAPER.parseMaterial()))
            builder.material(material)

            val customModelData = ConfigUtil.getIfValid(map, "custom-model-data", Int::class.java, 0)
            builder.customModelData(customModelData)

            val glow = ConfigUtil.getIfValid(map, "glow", Boolean::class.java, false)
            builder.glow(glow)

            val displays = HashMap<String, BindingDisplay>()
            for (type in ShatteredScrolls.getInstance().bindingTypes().all) {
                displays[type.id] = ConfigUtil.getIfValid(map, "",
                        BindingDisplay::class.java, BindingDisplay("Teleportation Scroll", false, LinkedList(), false, 0))
            }
            builder.displays(displays)

            val crafting = ConfigUtil.getIfValid(map, "crafting", ScrollCrafting::class.java,
                    ScrollCrafting())
            builder.crafting(crafting)

            val costData: CostData
            var costType: CostType?
            val costMap = map["cost"] as LinkedHashMap<String, Any>?
            if (costMap != null) {
                val costTypeId = ConfigUtil.getIfValid(costMap, "type", String::class.java, "none")
                costType = ShatteredScrolls.getInstance().costTypes()[costTypeId]
                if (costType == null) {
                    costType = NoneCostType()
                }
                costData = costType.deserialize(costMap)!!
            } else {
                costType = NoneCostType()
                costData = costType.deserialize(null)
            }
            builder.cost(costData)

            val infinite = ConfigUtil.getIfValid(map, "infinite", Boolean::class.java, false)
            builder.infinite(infinite)

            val defaultCharges = ConfigUtil.getIfValid(map, "default-charges", Int::class.java, 5)
            builder.defaultCharges(defaultCharges)

            val defaultBindingType = ConfigUtil.getIfValid(map, "binding-type", String::class.java, "unbound")
            builder.bindingData(ShatteredScrolls.getInstance().bindingTypes()[defaultBindingType].deserialize(
                    ConfigUtil.getIfValid(map, "binding-data",
                            LinkedHashMap::class.java, LinkedHashMap<String?, Any?>()) as LinkedHashMap<String?, Any?>))

            return builder.build()
        }
    }

}