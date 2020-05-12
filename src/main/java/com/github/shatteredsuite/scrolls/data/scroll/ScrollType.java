package com.github.shatteredsuite.scrolls.data.scroll;


import static com.github.shatteredsuite.core.config.ConfigUtil.getIfValid;

import com.github.shatteredsuite.core.config.ConfigException;
import com.github.shatteredsuite.core.config.ConfigUtil;
import com.github.shatteredsuite.core.include.xseries.XMaterial;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCost.CostType;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ScrollType")
public class ScrollType implements ConfigurationSerializable {
    public final String id;
    public final String name;
    public final Material material;
    public final int customModelData;
    public final BindingDisplay unboundMeta;
    public final BindingDisplay warpMeta;
    public final BindingDisplay locationMeta;
    public final ScrollCrafting crafting;
    public final ScrollCost cost;
    public final boolean infinite;
    public final int defaultCharges;
    public final BindingData bindingData;

    ScrollType(
        String id, String name,
        Material material, int customModelData, BindingData bindingData,
        BindingDisplay unboundMeta,
        BindingDisplay warpMeta,
        BindingDisplay locationMeta,
        ScrollCrafting crafting,
        ScrollCost cost,
        boolean infinite, int defaultCharges) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.customModelData = customModelData;
        this.unboundMeta = unboundMeta;
        this.bindingData = bindingData;
        this.warpMeta = warpMeta;
        this.locationMeta = locationMeta;
        this.crafting = crafting;
        this.cost = cost;
        this.infinite = infinite;
        this.defaultCharges = defaultCharges;
    }

    @SuppressWarnings("unused")
    public static ScrollType deserialize(Map<String, Object> map) throws ConfigException {
        Builder builder = new Builder();
        String id = getIfValid(map, "name", String.class, null);
        if(id == null) {
            throw new ConfigException("ScrollType id is required.");
        }
        builder.id(id);

        String name = getIfValid(map, "name", String.class, null);
        if(name == null) {
            throw new ConfigException("ScrollType id is required.");
        }
        builder.name(name);

        Material material = ConfigUtil.getMaterialOrDef(map, "material", XMaterial.PAPER.parseMaterial());
        builder.material(material);

        int customModelData = getIfValid(map, "custom-model-data", Integer.class, 0);
        builder.customModelData(customModelData);

        BindingDisplay unboundMeta = getIfValid(map, "unbound-meta", BindingDisplay.class,
            new BindingDisplay("Unbound Teleportation Scroll", "&7Default Lore."));
        builder.unboundMeta(unboundMeta);

        BindingDisplay locationMeta = getIfValid(map, "location-meta", BindingDisplay.class,
            new BindingDisplay("Teleportation Scroll", "&7Default Lore."));
        builder.locationMeta(locationMeta);

        BindingDisplay warpMeta = getIfValid(map, "warp-meta", BindingDisplay.class,
            new BindingDisplay("Warp Scroll", "&7Default Lore."));
        builder.warpMeta(warpMeta);

        ScrollCrafting crafting = getIfValid(map, "crafting", ScrollCrafting.class,
            new ScrollCrafting());
        builder.crafting(crafting);

        ScrollCost cost = getIfValid(map, "cost", ScrollCost.class,
            new ScrollCost(CostType.NONE, null));
        builder.cost(cost);

        boolean infinite = getIfValid(map, "infinite", Boolean.class, false);
        builder.infinite(infinite);

        int defaultCharges = getIfValid(map, "default-charges", Integer.class, 5);
        builder.defaultCharges(defaultCharges);

        BindingData bindingData = getIfValid(map, "default-binding", BindingData.class,
            new BindingData(BindingType.UNBOUND, null, null));
        builder.bindingData(bindingData);

        return builder.build();
    }

    @Override
    public Map<String, Object> serialize() {
        return ConfigUtil.reflectiveSerialize(this, ScrollType.class);
    }

    public ScrollInstance createInstance() {
        return new ScrollInstance(this, this.defaultCharges, this.infinite, this.bindingData);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        public String id;
        public String name;
        public Material material;
        public int customModelData;
        public BindingDisplay unboundMeta;
        public BindingDisplay warpMeta;
        public BindingDisplay locationMeta;
        public ScrollCrafting crafting;
        public ScrollCost cost;
        public boolean infinite;
        public int defaultCharges;
        public BindingData bindingData;

        public Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder customModelData(int customModelData) {
            this.customModelData = customModelData;
            return this;
        }

        public Builder unboundMeta(BindingDisplay unboundMeta) {
            this.unboundMeta = unboundMeta;
            return this;
        }

        public Builder warpMeta(BindingDisplay warpMeta) {
            this.warpMeta = warpMeta;
            return this;
        }

        public Builder locationMeta(BindingDisplay locationMeta) {
            this.locationMeta = locationMeta;
            return this;
        }

        public Builder crafting(ScrollCrafting crafting) {
            this.crafting = crafting;
            return this;
        }

        public Builder cost(ScrollCost cost) {
            this.cost = cost;
            return this;
        }

        public Builder infinite(boolean infinite) {
            this.infinite = infinite;
            return this;
        }

        public Builder defaultCharges(int defaultCharges) {
            this.defaultCharges = defaultCharges;
            return this;
        }

        public Builder bindingData(BindingData bindingData) {
            this.bindingData = bindingData;
            return this;
        }

        public ScrollType build() {
            return new ScrollType(id, name, material, customModelData, bindingData, unboundMeta,
                warpMeta, locationMeta, crafting, cost, infinite, defaultCharges);
        }
    }
}
