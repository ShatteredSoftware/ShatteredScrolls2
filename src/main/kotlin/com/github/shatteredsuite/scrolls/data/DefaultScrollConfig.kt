package com.github.shatteredsuite.scrolls.data

import com.github.shatteredsuite.core.config.ConfigRecipe
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData
import com.google.common.collect.Lists
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.World
import java.util.stream.Collectors

object DefaultScrollConfig {
    @JvmStatic
    val config: ScrollConfig = ScrollConfig("BindingScroll", false, 1000, ScrollCancelMode.UNBIND, Bukkit.getWorlds().stream().map { obj: World -> obj.name }.collect(
                    Collectors.toList()))

    @JvmStatic
    val scrollType = ScrollType("BindingScroll", "Unbound Scroll",
            Material.PAPER, UnboundBindingData(),
            displayHashMap, ScrollCrafting(defaultRecipe, Material.ENDER_PEARL, 2, 1), NoneCostData(), false, 5)

    private val defaultRecipe : ConfigRecipe
        get() {
            val recipe = ConfigRecipe()
            recipe.withItem('E', Material.ENDER_PEARL)
            recipe.withItem('P', Material.PAPER)
            recipe.withRow(0, " E ")
            recipe.withRow(1, "EPE")
            recipe.withRow(2, " E ")
            return recipe
        }

    private val displayHashMap: HashMap<String, BindingDisplay>
        get() {
            val displayHashMap = mutableMapOf<String, BindingDisplay>()
            displayHashMap["location"] = BindingDisplay("&rTeleportation Scroll", false,
                    Lists.newArrayList("&7It goes to &f%x% %y% %z% &7 in &f%world%&7.",
                            "&f%charges% &7charges.", "&7Right click to teleport."),
                    false, 4)
            displayHashMap["unbound"] = BindingDisplay("&rUnbound Scroll", false,
                    Lists.newArrayList("&f%charges% &7charges.",
                            "&7Right click to bind to your location."),
                    false, 2)
            displayHashMap["warp"] = BindingDisplay("&rWarp Scroll", false,
                    Lists.newArrayList("&f%charges% &7charges.",
                            "&7Right click to warp to &f%warp%&7."),
                    false, 2)
            return displayHashMap as HashMap<String, BindingDisplay>
        }
}