package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.entity.Player

/**
 * Handles general functionality related to scroll interaction.
 */
interface ScrollInteractor {
    /**
     * Specifies how this scroll should respond to interacting.
     *
     * @param instance The scroll that was clicked.
     * @param player The player who clicked the scroll.
     * @return The new scroll that the player should be given.
     */
    fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance
}