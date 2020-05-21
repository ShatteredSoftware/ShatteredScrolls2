package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.config.ConfigRecipe
import com.github.shatteredsuite.core.config.ConfigUtil
import com.github.shatteredsuite.core.include.xseries.XMaterial
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("ScrollCrafting")
class ScrollCrafting : ConfigurationSerializable {
    @JvmField
    val recipe: ConfigRecipe?
    @JvmField
    val repairMaterial: Material?
    val repairAmount: Int
    val craftAmount: Int

    @JvmField
    @Transient
    val craftable: Boolean

    @JvmField
    @Transient
    val repairable: Boolean

    /**
     * Default to non-craftable.
     */
    constructor() {
        repairMaterial = null
        recipe = null
        craftAmount = 0
        repairAmount = 0
        craftable = false
        repairable = false
    }

    /**
     * Constructor.
     *
     * @param recipe The recipe that should be used to craft this scroll.
     * @param repairMaterial The material that should be used to repair this scroll.
     * @param repairAmount The amount of charges to restore to the scroll.
     * @param craftAmount The amount of scrolls to craft.
     */
    constructor(recipe: ConfigRecipe?, repairMaterial: Material?, repairAmount: Int, craftAmount: Int) {
        this.recipe = recipe
        this.repairMaterial = repairMaterial
        this.repairAmount = repairAmount
        this.craftAmount = craftAmount
        craftable = recipe != null && recipe.valid && craftAmount > 0
        repairable = repairMaterial != null && repairMaterial != XMaterial.AIR.parseMaterial()
    }

    /**
     * ConfigurationSerializable serialization method.
     *
     * @return A serialized map of this.
     */
    override fun serialize(): Map<String, Any> {
        return ConfigUtil.reflectiveSerialize(this, ScrollCrafting::class.java)
    }

    companion object {
        /**
         * ConfigurationSerializable deserialization method.
         *
         * @param map The map to deserialize from.
         * @return the ScrollCrafting created from the map.
         */
        fun deserialize(map: Map<String?, Any?>?): ScrollCrafting {
            val recipe = ConfigUtil.getIfValid(map, "recipe", ConfigRecipe::class.java, null)
            val mat = ConfigUtil.getMaterialOrDef(map, "repair-material", XMaterial.ENDER_PEARL.parseMaterial())
            val repairAmount = ConfigUtil.getIfValid(map, "repair-amount", Int::class.java, 1)
            val craftAmount = ConfigUtil.getIfValid(map, "craft-amount", Int::class.java, 1)
            return ScrollCrafting(recipe, mat, repairAmount, craftAmount)
        }
    }
}