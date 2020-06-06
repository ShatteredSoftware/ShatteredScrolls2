package com.github.shatteredsuite.scrolls.config;

import com.github.shatteredsuite.core.config.ConfigRecipe;
import com.github.shatteredsuite.core.config.ConfigUtil;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class ScrollCraftingDTO implements ConfigurationSerializable {
    public ConfigRecipe recipe;
    public Material repairMaterial;
    public int repairAmount;
    public int craftAmount;

    public ScrollCraftingDTO(ConfigRecipe recipe, Material repairMaterial, int repairAmount,
        int craftAmount) {
        this.recipe = recipe;
        this.repairMaterial = repairMaterial;
        this.repairAmount = repairAmount;
        this.craftAmount = craftAmount;
    }

    public static ScrollCraftingDTO deserialize(Map<String, Object> map) {
        ConfigRecipe recipe = ConfigUtil.getIfValid(map, "recipe", ConfigRecipe.class, null);
        Material repairMaterial = ConfigUtil.getMaterialOrDef(map, "repair-material", Material.ENDER_PEARL);
        int repairAmount = ConfigUtil.getIfValid(map, "repair-amount", Integer.class, 1);
        int craftAmount = ConfigUtil.getIfValid(map, "craft-amount", Integer.class, 1);
        return new ScrollCraftingDTO(recipe, repairMaterial, repairAmount, craftAmount);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return ConfigUtil.reflectiveSerialize(this, ScrollCraftingDTO.class);
    }
}
