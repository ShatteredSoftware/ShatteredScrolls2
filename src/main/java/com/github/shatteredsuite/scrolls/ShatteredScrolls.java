package com.github.shatteredsuite.scrolls;

import com.earth2me.essentials.Essentials;
import com.github.shatteredsuite.core.ShatteredPlugin;
import com.github.shatteredsuite.core.cooldowns.CooldownManager;
import com.github.shatteredsuite.scrolls.api.BindingTypeAPI;
import com.github.shatteredsuite.scrolls.api.ContentAPI;
import com.github.shatteredsuite.scrolls.api.CostTypeAPI;
import com.github.shatteredsuite.scrolls.api.ScrollAPI;
import com.github.shatteredsuite.scrolls.api.WarpAPI;
import com.github.shatteredsuite.scrolls.commands.ScrollCommand;
import com.github.shatteredsuite.scrolls.data.ScrollConfig;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import com.github.shatteredsuite.scrolls.data.warp.WarpManager;
import com.github.shatteredsuite.scrolls.external.EssentialsConnector;
import com.github.shatteredsuite.scrolls.external.ExternalConnector;
import com.github.shatteredsuite.scrolls.listeners.CraftListener;
import com.github.shatteredsuite.scrolls.listeners.InteractListener;
import com.github.shatteredsuite.scrolls.recipe.RecipeHandler;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

public class ShatteredScrolls extends ShatteredPlugin implements ContentAPI {

    private static ShatteredScrolls instance;

    private final WarpManager warpManager = new WarpManager();
    private final ScrollTypeManager scrollTypeManager = new ScrollTypeManager();
    private final BindingTypeManager bindingTypeManager = new BindingTypeManager();
    private final CostTypeManager costTypeManager = new CostTypeManager();
    private final HashMap<String, ExternalConnector> connections = new HashMap<>();
    public CooldownManager cooldownManager;
    private ScrollConfig scrollConfig;

    public ShatteredScrolls() {
        instance = this;
        this.bStatsId = 5034;
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
    protected void load() throws Exception {
        Plugin ess = getServer().getPluginManager().getPlugin("Essentials");
        if (ess != null) {
            getLogger().info("Loading support for Essentials.");
            this.registerContent(ess, new EssentialsConnector((Essentials) ess));
        }
        super.load();
        ConfigurationSerialization.registerClass(BindingDisplay.class);
        ConfigurationSerialization.registerClass(ScrollCrafting.class);
        ConfigurationSerialization.registerClass(ScrollType.class);
        ConfigurationSerialization.registerClass(ScrollConfig.class);
    }

    @Override
    public void reloadContent() {
        for (ExternalConnector connector : this.connections.values()) {
            connector.addWarps(this);
            connector.addCostTypes(this);
            connector.addBindingTypes(this);
            connector.addBindingData(this);
            connector.addScrollTypes(this);
        }
    }

    private void loadLocations() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdirs();
        }
        File locationsFile = new File(getDataFolder(), "locations.yml");

        YamlConfiguration locationConfig = YamlConfiguration.loadConfiguration(locationsFile);
        ConfigurationSection section = locationConfig.getConfigurationSection("locations");
        if (section == null) {
            return;
        }
        for (String id : section.getKeys(false)) {
            this.warps().register((Warp) section.get(id));
        }
    }

    private void loadConfig() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdirs();
        }

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        reloadConfig();
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(configFile);
            // TODO: Why am I null?
            if (!yamlConfiguration.contains("config")) {
                HashMap<String, BindingDisplay> displayHashMap = new HashMap<>();
                displayHashMap.put("location", new BindingDisplay("Teleportation Scroll", false,
                    Lists.newArrayList("§7It goes to §f%x% %y% %z% §7 in §f%world%§7.",
                        "§f%charges% §7charges.", "§7Right click to teleport."),
                    false, 4));
                displayHashMap.put("unbound", new BindingDisplay("Unbound Scroll", false,
                    Lists.newArrayList("§f%charges% §7charges.",
                        "§7Right click to bind to your location."),
                    false, 2));
                displayHashMap.put("warp", new BindingDisplay("Warp Scroll", false,
                    Lists.newArrayList("§f%charges% §7charges.",
                        "§7Right click to warp to §f%warp%§7."),
                    false, 2));
                ScrollType defaultType = new ScrollType("BindingType", "Unbound Scroll",
                    Material.PAPER, 2, new UnboundBindingData(),
                    displayHashMap, new ScrollCrafting(), new NoneCostData(), false, 5);
            } else {
                this.scrollConfig = (ScrollConfig) Objects
                    .requireNonNull(yamlConfiguration.get("config"));
            }
        } catch (Exception err) {
            getLogger().log(Level.SEVERE, "Encountered an error while loading the config: ", err);
            this.setEnabled(false);
        }
    }

    public ScrollConfig config() {
        return this.scrollConfig;
    }

    @Override
    protected void postEnable() {
        super.postEnable();
        for (ExternalConnector connector : connections.values()) {
            connector.addBindingData(this);
            connector.addBindingTypes(this);
            connector.addCostTypes(this);
            connector.addScrollTypes(this);
            connector.addWarps(this);
        }
        loadConfig();
        loadLocations();
        cooldownManager = new CooldownManager(scrollConfig.getCooldown());
        RecipeHandler.registerRecipes(this);
        getCommand("scrolls").setExecutor(new ScrollCommand(this));
        getServer().getPluginManager().registerEvents(new CraftListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        this.content().reloadContent();
    }

    public void registerContent(Plugin plugin, ExternalConnector connector) {
        this.connections.put(plugin.getName(), connector);
    }
}
