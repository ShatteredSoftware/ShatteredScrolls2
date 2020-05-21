package com.github.shatteredsuite.scrolls.items

import org.bukkit.inventory.ItemStack

enum class NBTVersion(val nbtSpecifier: Int, val conversion: VersionConversion?) {
    VERSION_2(2, null),
    VERSION_1(1, object : VersionConversion {
        override fun convert(stack: ItemStack): ScrollInstance? {
            return VersionConverter.createScrollInstanceFromV1(stack)
        }
    }),
    NONE(0, null);

}