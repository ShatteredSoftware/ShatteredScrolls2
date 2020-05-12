package com.github.shatteredsuite.scrolls.items;

import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.core.include.nbt.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class NBTUtils {

    public static Location locationFromNBTItem(NBTItem compound, String offset) {
        double x = compound.getDouble(offset + "x");
        double y = compound.getDouble(offset + "y");
        double z = compound.getDouble(offset + "z");
        float yaw = compound.getFloat(offset + "yaw");
        float pitch = compound.getFloat(offset + "pitch");
        String worldName = compound.getString(offset + "world");
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static Location locationFromNBTCompound(NBTCompound compound, String offset) {
        double x = compound.getDouble(offset + "x");
        double y = compound.getDouble(offset + "y");
        double z = compound.getDouble(offset + "z");
        float yaw = compound.getFloat(offset + "yaw");
        float pitch = compound.getFloat(offset + "pitch");
        String worldName = compound.getString(offset + "world");
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static void addLocationNBT(NBTCompound compound, Location location) {
        compound.setDouble("x", location.getX());
        compound.setDouble("y", location.getY());
        compound.setDouble("z", location.getZ());
        compound.setFloat("yaw", location.getYaw());
        compound.setFloat("pitch", location.getPitch());
        if(location.getWorld() != null) {
            compound.setString("world", location.getWorld().getName());
        }
        else {
            compound.setString("world", Bukkit.getWorlds().get(0).getName());
        }
    }
}
