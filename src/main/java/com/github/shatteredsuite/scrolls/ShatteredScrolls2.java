package com.github.shatteredsuite.scrolls;

import com.earth2me.essentials.Essentials;
import com.github.shatteredsuite.core.ShatteredPlugin;
import com.github.shatteredsuite.core.cooldowns.CooldownManager;
import com.github.shatteredsuite.scrolls.api.BindingTypeAPI;
import com.github.shatteredsuite.scrolls.api.ContentAPI;
import com.github.shatteredsuite.scrolls.api.CostTypeAPI;
import com.github.shatteredsuite.scrolls.api.ScrollAPI;
import com.github.shatteredsuite.scrolls.api.WarpAPI;
import com.github.shatteredsuite.scrolls.data.ScrollConfig;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostTypeManager;
import com.github.shatteredsuite.scrolls.data.warp.WarpManager;
import com.github.shatteredsuite.scrolls.external.EssentialsConnector;
import com.github.shatteredsuite.scrolls.external.ExternalConnector;
import com.github.shatteredsuite.scrolls.listeners.CraftListener;
import com.github.shatteredsuite.scrolls.listeners.InteractListener;
import com.github.shatteredsuite.scrolls.recipe.RecipeHandler;
import java.util.HashMap;
import java.util.WeakHashMap;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

public class ShatteredScrolls2 extends ShatteredPlugin implements ContentAPI {

    private static ShatteredScrolls2 instance;

    private final WarpManager warpManager = new WarpManager();
    private final ScrollTypeManager scrollTypeManager = new ScrollTypeManager();
    private final BindingTypeManager bindingTypeManager = new BindingTypeManager();
    private final CostTypeManager costTypeManager = new CostTypeManager();
    private final HashMap<String, ExternalConnector> connections = new HashMap<>();
    private ScrollConfig scrollConfig;
    public final CooldownManager cooldownManager = new CooldownManager();

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

    public CostTypeAPI costTypes() {
        return costTypeManager;
    }

    public ContentAPI content() {
        return this;
    }

    @Override
    protected void load() throws Exception {
        Plugin ess = getServer().getPluginManager().getPlugin("Essentials");
        if(ess != null) {
            this.registerContent(ess, new EssentialsConnector((Essentials) ess));
        }
        super.load();
        ConfigurationSerialization.registerClass(BindingDisplay.class);
        ConfigurationSerialization.registerClass(ScrollCrafting.class);
        ConfigurationSerialization.registerClass(ScrollType.class);
    }

    public ScrollConfig config() {
        return this.scrollConfig;
    }

    @Override
    protected void postEnable() {
        super.postEnable();
        for(ExternalConnector connector : connections.values()) {
            connector.addBindingData(this);
            connector.addBindingTypes(this);
            connector.addCostTypes(this);
            connector.addScrollTypes(this);
            connector.addWarps(this);
        }
        RecipeHandler.registerRecipes(this);
        getServer().getPluginManager().registerEvents(new CraftListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
    }

    public void registerContent(Plugin plugin, ExternalConnector connector) {
        this.connections.put(plugin.getName(), connector);
    }
}
