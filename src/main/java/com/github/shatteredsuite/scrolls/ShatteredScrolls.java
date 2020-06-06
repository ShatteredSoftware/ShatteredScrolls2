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
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDataDeserializer;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDataSerializer;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.binding.LocationBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.binding.WarpBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostData;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostDataDeserializer;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostDataSerializer;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostTypeManager;
import com.github.shatteredsuite.scrolls.data.scroll.cost.HealthCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.HungerCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData;
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.PotionCostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.XPCostType;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import com.github.shatteredsuite.scrolls.data.warp.WarpManager;
import com.github.shatteredsuite.scrolls.external.EssentialsConnector;
import com.github.shatteredsuite.scrolls.external.ExternalConnector;
import com.github.shatteredsuite.scrolls.listeners.CraftListener;
import com.github.shatteredsuite.scrolls.listeners.InteractListener;
import com.github.shatteredsuite.scrolls.recipe.RecipeHandler;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

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
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BindingData.class, new BindingDataDeserializer(this));
        builder.registerTypeAdapter(BindingData.class, new BindingDataSerializer(this));
        builder.registerTypeAdapter(CostData.class, new CostDataDeserializer(this));
        builder.registerTypeAdapter(CostData.class, new CostDataSerializer(this));
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
        bindingTypeManager.register(new UnboundBindingType());
        bindingTypeManager.register(new WarpBindingType());
        bindingTypeManager.register(new LocationBindingType());
        costTypeManager.register(new NoneCostType());
        costTypeManager.register(new XPCostType());
        costTypeManager.register(new HealthCostType());
        costTypeManager.register(new HungerCostType());
        costTypeManager.register(new PotionCostType());
        Plugin ess = getServer().getPluginManager().getPlugin("Essentials");
        if (ess != null) {
            getLogger().info("Loading support for Essentials.");
            this.registerContent(ess, new EssentialsConnector((Essentials) ess));
        }
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

    public ScrollConfig config() {
        return this.scrollConfig;
    }

    private void readConfig() {
        File file = new File(getDataFolder(), "config.json");
        try {
            this.scrollConfig = gson.fromJson(new FileReader(file), ScrollConfig.class);
        } catch (FileNotFoundException e) {
            getLogger().warning("Config invalid or not found. Generating a default one.");
            HashMap<String, BindingDisplay> displayHashMap = new HashMap<>();
            displayHashMap.put("location", new BindingDisplay("Teleportation Scroll", false,
                Lists.newArrayList("&7It goes to &f%x% %y% %z% &7 in &f%world%&7.",
                    "&f%charges% &7charges.", "&7Right click to teleport."),
                false, 4));
            displayHashMap.put("unbound", new BindingDisplay("Unbound Scroll", false,
                Lists.newArrayList("&f%charges% &7charges.",
                    "&7Right click to bind to your location."),
                false, 2));
            displayHashMap.put("warp", new BindingDisplay("Warp Scroll", false,
                Lists.newArrayList("&f%charges% &7charges.",
                    "&7Right click to warp to &f%warp%&7."),
                false, 2));
            ScrollType defaultType = new ScrollType("BindingScroll", "Unbound Scroll",
                Material.PAPER, 2, new UnboundBindingData(),
                displayHashMap, new ScrollCrafting(), new NoneCostData(), false, 5);
            this.scrollConfig = new ScrollConfig("BindingScroll", false, 1000, Bukkit.getWorlds().stream().map(
                World::getName).collect(
                Collectors.toList()), defaultType);
            try {
                String configText = gson.toJson(this.scrollConfig);
                getLogger().info(configText);
                Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                writer.write(configText);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        for(ScrollType type : this.scrollConfig.scrollTypes) {
            scrollTypeManager.register(type.getId(), type);
        }
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
        readConfig();
        loadLocations();
        cooldownManager = new CooldownManager(scrollConfig.cooldown);
//        RecipeHandler.registerRecipes(this);
        getCommand("scrolls").setExecutor(new ScrollCommand(this));
        getServer().getPluginManager().registerEvents(new CraftListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        this.content().reloadContent();
    }

    public void registerContent(Plugin plugin, ExternalConnector connector) {
        this.connections.put(plugin.getName(), connector);
    }
}
