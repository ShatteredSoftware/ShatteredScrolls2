package com.github.shatteredsuite.scrolls.config;

import java.util.Map;

public class ScrollTypeDTO {
    public String id;
    public String name;
    public String material;
    public ScrollCraftingDTO scrollCrafting;
    public CostDataDTO costData;
    public boolean infinite;
    public int defaultCharges;
    public BindingDataDTO bindingData;
    public Map<String, BindingDisplayDTO> displays;
}
