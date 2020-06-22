package com.github.shatteredsuite.scrolls.listeners

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinLeaveListener(private val plugin: ShatteredScrolls) : Listener {
    fun onJoin(event: PlayerJoinEvent) {
        for(scroll in plugin.scrolls().all) {
            println("Adding scroll ${scroll.id} crafting to ${event.player.name}")
            event.player.discoverRecipe(scroll.crafting.key ?: continue)
        }
    }
}