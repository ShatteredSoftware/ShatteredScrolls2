package com.github.shatteredsuite.scrolls.listeners

import com.github.shatteredsuite.scrolls.ShatteredScrolls2
import com.github.shatteredsuite.scrolls.items.ScrollInstance.Companion.fromItemStack
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack

class CraftListener(private val instance: ShatteredScrolls2) : Listener {
    @EventHandler
    fun craftPreprocess(event: PrepareItemCraftEvent) {
        val matrix = event.inventory.matrix
        if (event.recipe == null) {
            return
        }
        val inst = fromItemStack(event.recipe!!.result) ?: return
        for ((index, stack) in matrix.withIndex()) {
            val stackInstance = fromItemStack(event.recipe!!.result)
            if (stackInstance != null) {
                for ((innerIndex, innerStack) in matrix.withIndex()) {
                    if(innerIndex == index) {
                        continue
                    }
                    if(innerStack.type != stackInstance.scrollType.crafting.repairMaterial) {
                        event.inventory.result = null
                        return
                    }
                }
                break
            }
        }
    }

}