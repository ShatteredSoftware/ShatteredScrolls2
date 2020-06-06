package com.github.shatteredsuite.scrolls.config;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BindingDisplayDTO {
    @SerializedName("for")
    String type;
    String name;
    List<String> lore;
    boolean glow;
    int customModelData;

    public BindingDisplayDTO(String type, String name, List<String> lore, boolean glow,
        int customModelData) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.glow = glow;
        this.customModelData = customModelData;
    }
}
