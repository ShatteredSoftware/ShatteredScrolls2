package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.items.NBTUtils
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import java.util.*

class LocationBindingData(val location: Location) : BindingData("location", BindingDisplay("Teleportation Scroll", false, LinkedList())) {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        val event = PlayerTeleportEvent(player, player.location, location, PlayerTeleportEvent.TeleportCause.PLUGIN)
        Bukkit.getServer().pluginManager.callEvent(event)
        return if (!event.isCancelled) {
            instance
        } else ScrollInstance(instance.scrollType, if (instance.isInfinite) instance.charges else instance.charges - 1, instance.isInfinite, instance.bindingData)
    }

    public override fun applyBindingNBT(compound: NBTCompound) {
        NBTUtils.addLocationNBT(compound, location)
    }

    override fun serialize(): Map<String?, Any?> {
        return mapOf("location" to this.location)
    }

}