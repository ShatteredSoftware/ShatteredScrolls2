package com.github.shatteredsuite.scrolls;

import com.earth2me.essentials.Essentials;
import com.github.shatteredsuite.core.ShatteredPlugin;
import com.github.shatteredsuite.core.cooldowns.CooldownManager;
import com.github.shatteredsuite.scrolls.api.BindingTypeAPI;
import com.github.shatteredsuite.scrolls.api.ContentAPI;
import com.github.shatteredsuite.scrolls.api.CostTypeAPI;
import com.github.shatteredsuite.scrolls.api.ScrollAPI;
import com.github.shatteredsuite.scrolls.api.WarpAPI;
import com.github.shatteredsuite.scrolls.data.scroll.LocationDeserializer;
import com.github.shatteredsuite.scrolls.data.scroll.LocationSerializer;
import com.github.shatteredsuite.scrolls.data.scroll.commands.BaseCommand;
import com.github.shatteredsuite.scrolls.data.ScrollConfig;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDataDeserializer;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDataSerializer;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.binding.LocationBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.binding.WarpBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostData;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostDataDeserializer;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostDataSerializer;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.cost.HealthCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.HungerCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.PotionCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.XPCostType;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import com.github.shatteredsuite.scrolls.data.warp.WarpManager;
import com.github.shatteredsuite.scrolls.external.EssentialsConnector;
import com.github.shatteredsuite.scrolls.external.ExternalConnector;
import com.github.shatteredsuite.scrolls.listeners.CraftListener;
import com.github.shatteredsuite.scrolls.listeners.InteractListener;
import com.github.shatteredsuite.scrolls.listeners.JoinLeaveListener;
import com.github.shatteredsuite.scrolls.recipe.RecipeHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ShatteredScrolls extends ShatteredPlugin implements ContentAPI {

    private static ShatteredScrolls instance;

    private final WarpManager warpManager = new WarpManager();
    private final ScrollTypeManager scrollTypeManager = new ScrollTypeManager();
    private final BindingTypeManager bindingTypeManager = new BindingTypeManager();
    private final CostTypeManager costTypeManager = new CostTypeManager();
    private final HashMap<String, ExternalConnector> connections = new HashMap<>();
    public final Gson gson;
    public CooldownManager cooldownManager;
    private ScrollConfig scrollConfig;

    public ShatteredScrolls() {
        instance = this;
        this.bStatsId = 5034;
        this.internalConfig = false;
        this.createMessages = true;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BindingData.class, new BindingDataDeserializer(this));
        builder.registerTypeAdapter(BindingData.class, new BindingDataSerializer(this));
        builder.registerTypeAdapter(CostData.class, new CostDataDeserializer(this));
        builder.registerTypeAdapter(CostData.class, new CostDataSerializer(this));
        builder.registerTypeAdapter(Location.class, new LocationDeserializer());
        builder.registerTypeAdapter(Location.class, new LocationSerializer(this));
        this.gson = builder.setPrettyPrinting().create();
    }

    public static ShatteredScrolls getInstance() {
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
    protected void preload() {
    }

    @Override
    protected void load() throws Exception {
        loadDefaultContent();
        Plugin ess = getServer().getPluginManager().getPlugin("Essentials");
        if (ess != null) {
            getLogger().info("Loading support for Essentials.");
            this.registerContent(ess, new EssentialsConnector((Essentials) ess));
        }
    }

    private void loadDefaultContent() {
        bindingTypeManager.register(new UnboundBindingType());
        bindingTypeManager.register(new WarpBindingType());
        bindingTypeManager.register(new LocationBindingType());
        costTypeManager.register(new NoneCostType());
        costTypeManager.register(new XPCostType());
        costTypeManager.register(new HealthCostType());
        costTypeManager.register(new HungerCostType());
        costTypeManager.register(new PotionCostType());
    }

    @Override
    public void connectContent() {
        for (ExternalConnector connector : this.connections.values()) {
            connector.addWarps(this);
            connector.addCostTypes(this);
            connector.addBindingTypes(this);
            connector.addBindingData(this);
            connector.addScrollTypes(this);
        }
    }

    public ScrollConfig config() {
        return this.scrollConfig;
    }

    @Override
    protected void postEnable() {
        super.postEnable();
        loadContent();
        cooldownManager = new CooldownManager(scrollConfig.cooldown);
        RecipeHandler.registerRecipes(this);
        getCommand("scrolls").setExecutor(new BaseCommand(this));
        registerEvents();
    }

    public void loadContent() {
        for (ExternalConnector connector : connections.values()) {
            connector.addBindingData(this);
            connector.addBindingTypes(this);
            connector.addCostTypes(this);
            connector.addScrollTypes(this);
            connector.addWarps(this);
        }
        this.scrollConfig = ConfigManager.loadConfig(this);
        List<Warp> warps = ConfigManager.loadWarps(this);
        for(Warp warp : warps) {
            warps().register(warp);
        }
        List<ScrollType> scrolls = ConfigManager.loadScrolls(this);
        for(ScrollType scroll : scrolls) {
            scrolls().register(scroll);
        }
        this.content().connectContent();
    }

    public void saveContent() {
        ConfigManager.save(this);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new CraftListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
    }

    @Override
    public void onDisable() {
        ConfigManager.save(this);
    }

    public void registerContent(Plugin plugin, ExternalConnector connector) {
        this.connections.put(plugin.getName(), connector);
    }
}
