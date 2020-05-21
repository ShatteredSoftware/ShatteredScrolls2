package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.items.NBTUtils
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import java.util.*

class LocationBindingData(val location: Location) : BindingData("location", BindingDisplay("Teleportation Scroll", false, LinkedList())) {
    override fun onInteract(item: ScrollInstance, player: Player): ScrollInstance {
        val event = PlayerTeleportEvent(player, player.location, location, PlayerTeleportEvent.TeleportCause.PLUGIN)
        Bukkit.getServer().pluginManager.callEvent(event)
        return if (!event.isCancelled) {
            item
        } else ScrollInstance(item.scrollType, if (item.isInfinite) item.charges else item.charges - 1, item.isInfinite, item.bindingData)
    }

    public override fun applyBindingNBT(compound: NBTCompound) {
        NBTUtils.addLocationNBT(compound, location)
    }

    override fun serialize(): Map<String?, Any?>? {
        return mapOf("location" to this.location)
    }

}