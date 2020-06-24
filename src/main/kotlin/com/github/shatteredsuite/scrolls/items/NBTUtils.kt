package com.github.shatteredsuite.scrolls.items

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.core.include.nbt.NBTItem
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

object NBTUtils {
    fun locationFromNBTItemUUID(compound: NBTItem, offset: String): Location {
        val x = compound.getDouble(offset + "x")
        val y = compound.getDouble(offset + "y")
        val z = compound.getDouble(offset + "z")
        val yaw = compound.getFloat(offset + "yaw")
        val pitch = compound.getFloat(offset + "pitch")
        val worldName = compound.getString(offset + "world")
        val world = Bukkit.getWorld(UUID.fromString(worldName))
        return Location(world, x, y, z, yaw, pitch)
    }

    fun locationFromNBTItem(compound: NBTItem, offset: String): Location {
        val x = compound.getDouble(offset + "x")
        val y = compound.getDouble(offset + "y")
        val z = compound.getDouble(offset + "z")
        val yaw = compound.getFloat(offset + "yaw")
        val pitch = compound.getFloat(offset + "pitch")
        val worldName = compound.getString(offset + "world")
        val world = Bukkit.getWorld(worldName)
        return Location(world, x, y, z, yaw, pitch)
    }

    fun locationFromNBTCompound(compound: NBTCompound, offset: String): Location {
        val x = compound.getDouble(offset + "x")
        val y = compound.getDouble(offset + "y")
        val z = compound.getDouble(offset + "z")
        val yaw = compound.getFloat(offset + "yaw")
        val pitch = compound.getFloat(offset + "pitch")
        val worldName = compound.getString(offset + "world")
        val world = Bukkit.getWorld(worldName)
        return Location(world, x, y, z, yaw, pitch)
    }

    fun addLocationNBT(compound: NBTCompound, location: Location) {
        compound.setDouble("x", location.x)
        compound.setDouble("y", location.y)
        compound.setDouble("z", location.z)
        compound.setFloat("yaw", location.yaw)
        compound.setFloat("pitch", location.pitch)
        if (location.world != null) {
            compound.setString("world", location.world!!.name)
        } else {
            compound.setString("world", Bukkit.getWorlds()[0].name)
        }
    }
}