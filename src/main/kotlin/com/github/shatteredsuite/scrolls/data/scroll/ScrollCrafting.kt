package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.config.ConfigRecipe
import com.github.shatteredsuite.core.include.xseries.XMaterial
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Material
import org.bukkit.NamespacedKey

class ScrollCrafting {
    @JvmField
    val recipe: ConfigRecipe?
    @Transient
    var key: NamespacedKey? = null
        private set

    @JvmField
    val repairMaterial: Material?
    val repairAmount: Int
    val craftAmount: Int

    val craftable: Boolean
        get() = recipe != null && craftAmount > 0

    val repairable: Boolean
        get() = repairMaterial != null && repairAmount > 0 && repairMaterial != XMaterial.AIR.parseMaterial()

    /**
     * Default to non-craftable.
     */
    constructor() {
        repairMaterial = null
        recipe = null
        craftAmount = 0
        repairAmount = 0
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
    }

    fun setKey(plugin: ShatteredScrolls, type: ScrollType) {
        this.key = NamespacedKey(plugin, type.id)
    }
}