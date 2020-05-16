package com.github.shatteredsuite.scrolls.items;

import com.github.shatteredsuite.core.include.nbt.NBTItem;
import com.github.shatteredsuite.core.include.nbt.NBTCompound;
import com.github.shatteredsuite.scrolls.ShatteredScrolls2;
import com.github.shatteredsuite.scrolls.data.scroll.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.BindingType;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.UnboundBindingData;
import org.bukkit.inventory.ItemStack;

public class ScrollInstance extends ItemStack {
    public final ScrollType scrollType;
    public int charges;
    public final boolean isInfinite;
    public final BindingData bindingData;
    public static final NBTVersion currentNbtVersion = com.github.shatteredsuite.scrolls.items.NBTVersion.VERSION_2;

    public ScrollInstance(ScrollType scrollType, int charges, boolean isInfinite, BindingData bindingType) {
        super(scrollType.material);
        this.scrollType = scrollType;
        this.charges = charges;
        this.isInfinite = isInfinite;
        this.bindingData = bindingType;
        this.applyNBT();
    }

    public static ScrollInstance fromItemStack(ItemStack stack) {
        NBTItem item = new NBTItem(stack);
        NBTVersion version = getNBTVersion(item);
        if (version != currentNbtVersion) {
            return version.conversion.convert(stack);
        }
        return fromCurrentStack(item);
    }

    private static NBTVersion getNBTVersion(NBTItem item) {
        if(item.hasKey("shatteredscrolls")) {
            NBTCompound compound = item.getCompound("shatteredscrolls");
            return getVersionFromCompound(compound);
        }
        else if(item.hasKey("shatteredscrolls_bound")) {
            return com.github.shatteredsuite.scrolls.items.NBTVersion.VERSION_1;
        }
        return com.github.shatteredsuite.scrolls.items.NBTVersion.NONE;
    }

    private static NBTVersion getVersionFromCompound(NBTCompound compound) {
        int version = compound.getInteger("version");
        if(version == 2) {
            return com.github.shatteredsuite.scrolls.items.NBTVersion.VERSION_2;
        }
        return com.github.shatteredsuite.scrolls.items.NBTVersion.NONE;
    }

    private static ScrollInstance fromCurrentStack(NBTItem item) {
        NBTCompound comp = item.getCompound("shatteredscrolls");
        String scrollTypeName = comp.getString("type");
        ScrollType scrollType = ShatteredScrolls2.getInstance().scrolls().get(scrollTypeName);
        int charges = comp.getInteger("charges");
        boolean infinite = comp.getBoolean("infinite");
        BindingData bindingData = readBindingData(comp);
        return new ScrollInstance(scrollType, charges, infinite, bindingData);
    }

    private static BindingData readBindingData(NBTCompound comp) {
        BindingData bindingData;
        NBTCompound binding = comp.getCompound("binding");
        if (binding != null) {
            String bindingName = binding.getString("type");
            bindingData = ShatteredScrolls2.getInstance().bindingTypes().get(bindingName).fromNBT(binding);
        }
        else {
            bindingData = new UnboundBindingData();
        }
        return bindingData;
    }

    private void applyNBT() {
        NBTItem nbti = new NBTItem(this);
        NBTCompound baseCompound = nbti.addCompound("shatteredscrolls");
        baseCompound.setString("type", this.scrollType.id);
        baseCompound.setInteger("charges", this.charges);
        baseCompound.setBoolean("infinite", this.isInfinite);
        baseCompound.setInteger("version", currentNbtVersion.nbtSpecifier);
        NBTCompound binding = baseCompound.addCompound("binding");
        binding.setString("type", this.bindingData.type);
        this.bindingData.applyNBT(nbti);
    }
}
