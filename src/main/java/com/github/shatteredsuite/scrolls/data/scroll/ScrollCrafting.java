package com.github.shatteredsuite.scrolls.data.scroll;

import static com.github.shatteredsuite.core.config.ConfigUtil.getIfValid;
import static com.github.shatteredsuite.core.config.ConfigUtil.getMaterialOrDef;
import static com.github.shatteredsuite.core.config.ConfigUtil.reflectiveSerialize;

import com.github.shatteredsuite.core.config.ConfigRecipe;
import com.github.shatteredsuite.core.include.xseries.XMaterial;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ScrollCrafting")
public class ScrollCrafting implements ConfigurationSerializable {
    public final ConfigRecipe recipe;
    public final Material repairMaterial;
    public final int craftAmount;
    public final boolean craftable;
    public final boolean repairable;

    /**
     * Default to non-craftable.
     */
    public ScrollCrafting() {
        this.repairMaterial = null;
        this.recipe = null;
        this.craftAmount = 0;
        this.craftable = false;
        this.repairable = false;
    }

    /**
     * Constructor.
     *
     * @param recipe The recipe that should be used to craft this scroll.
     * @param repairMaterial The material that should be used to repair this scroll.
     * @param craftAmount The amount of scrolls to craft.
     */
    public ScrollCrafting(ConfigRecipe recipe, Material repairMaterial, int craftAmount) {
        this.recipe = recipe;
        this.repairMaterial = repairMaterial;
        this.craftAmount = craftAmount;
        this.craftable = recipe != null && recipe.valid && craftAmount > 0;
        this.repairable = repairMaterial != null && repairMaterial != XMaterial.AIR.parseMaterial();
    }

    /**
     * ConfigurationSerializable deserialization method.
     *
     * @param map The map to deserialize from.
     * @return the ScrollCrafting created from the map.
     */
    public static ScrollCrafting deserialize(Map<String, Object> map) {
        ConfigRecipe recipe = getIfValid(map, "recipe", ConfigRecipe.class, null);
        Material mat = getMaterialOrDef(map, "repair-material", XMaterial.ENDER_PEARL.parseMaterial());
        int craftAmount = getIfValid(map, "craft-amount", Integer.class, 1);
        return new ScrollCrafting(recipe, mat, craftAmount);
    }

    /**
     * ConfigurationSerializable serialization method.
     *
     * @return A serialized map of this.
     */
    @Override
    public Map<String, Object> serialize() {
        return reflectiveSerialize(this, ScrollCrafting.class);
    }
}
