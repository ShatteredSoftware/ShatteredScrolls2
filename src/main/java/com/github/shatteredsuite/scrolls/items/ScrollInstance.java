package com.github.shatteredsuite.scrolls.items;

import com.github.shatteredsuite.core.include.nbt.NBTItem;
import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.scrolls.ShatteredScrolls2;
import com.github.shatteredsuite.scrolls.data.scroll.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.BindingType;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class ScrollInstance extends ItemStack {
    private final ScrollType scrollType;
    private int charges;
    private final boolean isInfinite;
    private final BindingData bindingData;
    private static final int nbt_version = 2;

    public static ScrollInstance fromItemStack(ItemStack stack) {
        NBTItem item = new NBTItem(stack);
        NBTCompound comp = item.getCompound("teleportationscrolls");
        if (comp == null) {
            if (item.hasKey("shatteredscrolls_bound")) {
                return VersionConverter.v1_v2(stack);
            }
            return null;
        }
        String scrollTypeName = comp.getString("type");
        ScrollType scrollType = ShatteredScrolls2.getInstance().scrolls().get(scrollTypeName);
        int charges = comp.getInteger("charges");
        boolean infinite = comp.getBoolean("infinite");
        BindingData bindingData = new BindingData();
        NBTCompound binding = comp.getCompound("binding");
        if (binding != null) {
            String bindingType = binding.getString("type");
            if (bindingType.equalsIgnoreCase("WARP")) {
                String warpName = binding.getString("warp");
                Warp warp = ShatteredScrolls2.getInstance().warps().get(warpName);
                if (warp != null) {
                    bindingData = new BindingData(warp);
                } else {
                    charges += 1;
                }
            } else if (bindingType.equalsIgnoreCase("LOCATION")) {
                double x = binding.getDouble("x");
                double y = binding.getDouble("y");
                double z = binding.getDouble("z");
                float pitch = binding.getFloat("pitch");
                float yaw = binding.getFloat("yaw");
                String worldName = binding.getString("x");
                World world = Bukkit.getWorld(worldName);
                Location location = new Location(world, x, y, z, yaw, pitch);
                bindingData = new BindingData(location);
            }
        }
        return new ScrollInstance(scrollType, charges, infinite, bindingData);
    }

    public static ItemStack applyNBT(ScrollInstance base) {
        NBTItem nbti = new NBTItem(base);
        NBTCompound baseCompound = nbti.addCompound("teleportationscrolls");
        baseCompound.setString("type", base.scrollType.id);
        baseCompound.setInteger("charges", base.charges);
        baseCompound.setBoolean("infinite", base.isInfinite);
        NBTCompound binding = baseCompound.addCompound("binding");
        binding.setString("type", base.bindingData.bindingType.name());
        if(base.bindingData.bindingType == BindingType.LOCATION) {
            NBTUtils.addLocationNBT(binding, base.bindingData.location);
        }
        else if(base.bindingData.bindingType == BindingType.WARP) {
            binding.setString("warp", base.bindingData.warp.id);
        }
        return nbti.getItem();
    }

    public ScrollInstance(ScrollType scrollType, int charges, boolean isInfinite, BindingData bindingMeta) {
        super(scrollType.material);

        this.scrollType = scrollType;
        this.charges = charges;
        this.isInfinite = isInfinite;
        this.bindingData = bindingMeta;
    }
}
