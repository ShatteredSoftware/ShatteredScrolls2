package com.github.shatteredsuite.scrolls.listeners

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import com.github.shatteredsuite.scrolls.items.ScrollInstance.Companion.fromItemStack
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

class CraftListener(private val instance: ShatteredScrolls) : Listener {
    @EventHandler
    fun craftPreprocess(event: PrepareItemCraftEvent) {
        val matrix = event.inventory.matrix
        for ((index, stack) in matrix.withIndex()) {
            val stackInstance = fromItemStack(stack) ?: continue
            if (!event.view.player.hasPermission("shatteredscrolls.craft")) {
                event.inventory.result = null
                return
            }
            if (stackInstance.isInfinite) {
                event.inventory.result = null
                return
            }
            var count = 0
            for ((innerIndex, innerStack) in matrix.withIndex()) {
                if(innerStack == null) {
                    continue
                }
                if (innerIndex == index) {
                    continue
                }
                if (innerStack.type != stackInstance.scrollType.crafting.repairMaterial) {
                    event.inventory.result = null
                    return
                } else {
                    count++
                }
            }
            if(count == 0) {
                event.inventory.result = null
                return
            }
            val inst = ScrollInstance(stackInstance.scrollType, stackInstance.charges + count * stackInstance.scrollType.crafting.repairAmount, stackInstance.isInfinite, stackInstance.bindingData)
            event.inventory.result = inst.toItemStack()
            break
        }
    }

}