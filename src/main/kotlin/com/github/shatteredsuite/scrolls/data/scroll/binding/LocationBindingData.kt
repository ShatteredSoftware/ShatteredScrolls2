package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.ScrollCancelMode
import com.github.shatteredsuite.scrolls.items.NBTUtils
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import java.util.*

class LocationBindingData(val location: Location) : BindingData("location", BindingDisplay("Teleportation Scroll", false, LinkedList(), false, 0)) {
    override fun onInteract(instance: ScrollInstance, player: Player): ScrollInstance {
        val event = PlayerTeleportEvent(player, player.location, location, PlayerTeleportEvent.TeleportCause.PLUGIN)
        Bukkit.getServer().pluginManager.callEvent(event)
        return if (!event.isCancelled) {
            if(location.world == null) {
                val inst = ShatteredScrolls.getInstance()
                return if (inst.config().cancelMode != ScrollCancelMode.UNBIND) {
                    inst.messenger.sendMessage(player, "unknown-world-unbind", true)
                    instance
                } else {
                    inst.messenger.sendMessage(player, "unknown-world-cancel", true)
                    ScrollInstance(instance.scrollType, instance.charges, instance.isInfinite, UnboundBindingData())
                }
            }
            player.teleport(location)
            ScrollInstance(instance.scrollType, instance.charges - 1, instance.isInfinite, instance.bindingData)
        }
        else instance
    }

    public override fun applyBindingNBT(compound: NBTCompound) {
        NBTUtils.addLocationNBT(compound, location)
    }

    override fun serialize(): Map<String?, Any?> {
        return mapOf("location" to this.location)
    }

    override fun parsePlaceholders(name: String): String {
        return name.replace("%x%", location.blockX.toString())
                .replace("%y%", location.blockY.toString())
                .replace("%z%", location.blockZ.toString())
                .replace("%yaw%", location.yaw.toString())
                .replace("%pitch%", location.pitch.toString())
                .replace("%world%", location.world?.name ?: "Unknown World")
    }
}