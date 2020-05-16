package com.github.shatteredsuite.scrolls;

import com.github.shatteredsuite.core.ShatteredPlugin;
import com.github.shatteredsuite.scrolls.api.BindingTypeAPI;
import com.github.shatteredsuite.scrolls.api.ScrollAPI;
import com.github.shatteredsuite.scrolls.api.WarpAPI;
import com.github.shatteredsuite.scrolls.data.ScrollConfig;
import com.github.shatteredsuite.scrolls.data.scroll.BindingTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.BindingDisplay;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCost;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollTypeManager;
import com.github.shatteredsuite.scrolls.data.warp.WarpManager;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class ShatteredScrolls2 extends ShatteredPlugin {

    private static ShatteredScrolls2 instance;

    private final WarpManager warpManager = new WarpManager();
    private final ScrollTypeManager scrollTypeManager = new ScrollTypeManager();
    private final BindingTypeManager bindingTypeManager = new BindingTypeManager();
    private ScrollConfig scrollConfig;

    public ShatteredScrolls2() {
        instance = this;
        this.bStatsId = 7499;
    }

    public static ShatteredScrolls2 getInstance() {
        return instance;
    }

    public WarpAPI warps() {
        return warpManager;
    }

    public ScrollAPI scrolls() {
        return scrollTypeManager;
    }

    public BindingTypeAPI bindingTypes() {
        return bindingTypeManager;
    }

    @Override
    protected void load() throws Exception {
        super.load();
        ConfigurationSerialization.registerClass(BindingDisplay.class);
        ConfigurationSerialization.registerClass(ScrollCost.class);
        ConfigurationSerialization.registerClass(ScrollCrafting.class);
        ConfigurationSerialization.registerClass(ScrollType.class);
    }

    public ScrollConfig config() {
        return this.scrollConfig;
    }

    @Override
    protected void postEnable() {
        super.postEnable();
    }
}
