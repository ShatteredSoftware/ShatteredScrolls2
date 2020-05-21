package com.github.shatteredsuite.scrolls.items

import org.bukkit.inventory.ItemStack

interface VersionConversion {
    fun convert(stack: ItemStack): ScrollInstance?
}