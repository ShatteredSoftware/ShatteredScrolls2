package com.github.shatteredsuite.scrolls;

import com.earth2me.essentials.Essentials;
import com.github.shatteredsuite.core.ShatteredPlugin;
import com.github.shatteredsuite.core.cooldowns.CooldownManager;
import com.github.shatteredsuite.scrolls.api.BindingTypeAPI;
import com.github.shatteredsuite.scrolls.api.ContentAPI;
import com.github.shatteredsuite.scrolls.api.CostTypeAPI;
import com.github.shatteredsuite.scrolls.api.ScrollAPI;
import com.github.shatteredsuite.scrolls.api.WarpAPI;
import com.github.shatteredsuite.scrolls.data.DefaultScrollConfig;
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
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ShatteredScrolls extends ShatteredPlugin implements ContentAPI {

    private static ShatteredScrolls instance;

    private WarpManager warpManager = new WarpManager();
    private ScrollTypeManager scrollTypeManager = new ScrollTypeManager();
    private BindingTypeManager bindingTypeManager = new BindingTypeManager();
    private CostTypeManager costTypeManager = new CostTypeManager();
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

    private void readWarps() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdirs();
        }
        File warpsFile = new File(getDataFolder(), "warps.json");
        ArrayList<Warp> warps;
        Type type = new TypeToken<ArrayList<Warp>>(){}.getType();
        try {
            warps = gson.fromJson(new FileReader(warpsFile), type);
        } catch (FileNotFoundException e) {
            warps = new ArrayList<>();
        }
        for (Warp warp : warps) {
            this.warps().register(warp);
        }
    }

    private void writeWarps() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdirs();
        }
        List<Warp> warps = new LinkedList<>();
        Iterables.addAll(warps, this.warpManager.getAll());
        warps = warps.stream().filter(it -> !it.getExternal()).collect(Collectors.toList());
        File file = new File(getDataFolder(), "warps.json");
        try {
            String configText = gson.toJson(warps);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(configText);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ScrollConfig config() {
        return this.scrollConfig;
    }

    private void readConfig() {
        File file = new File(getDataFolder(), "config.json");
        boolean invalid = false;
        try {
            this.scrollConfig = gson.fromJson(new FileReader(file), ScrollConfig.class);
            if(scrollConfig == null) {
                invalid = true;
            }
        } catch (FileNotFoundException e) {
            invalid = true;
        }
        if(invalid) {
            defaultConfig(file);
        }
        for(ScrollType type : this.scrollConfig.scrollTypes) {
            scrollTypeManager.register(type.getId(), type);
        }
    }

    private void defaultConfig(File file) {
        getLogger().warning("Config invalid or not found. Generating a default one.");
        this.scrollConfig = DefaultScrollConfig.getConfig();
        try {
            String configText = gson.toJson(this.scrollConfig);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(configText);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeConfig() {
        File file = new File(getDataFolder(), "config.json");
        try {
            String configText = gson.toJson(this.scrollConfig);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(configText);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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
        loadScrolls();
        readWarps();
        cooldownManager = new CooldownManager(scrollConfig.cooldown);
        RecipeHandler.registerRecipes(this);
        getCommand("scrolls").setExecutor(new BaseCommand(this));
        getServer().getPluginManager().registerEvents(new CraftListener(this), this);
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
        this.content().reloadContent();
    }

    private void loadScrolls() {
        for(ScrollType type : this.scrollConfig.scrollTypes) {
            scrolls().register(type.getId(), type);
            if(type.getCrafting().getCraftable()) {
                type.getCrafting().setKey(this, type);
            }
        }
    }

    public void readFromDisk() {
        warpManager = new WarpManager();
        scrollTypeManager = new ScrollTypeManager();
        bindingTypeManager = new BindingTypeManager();
        costTypeManager = new CostTypeManager();
        try {
            load();
        } catch (Exception ex) {
            getLogger().severe("Failed to initialize. Disabling plugin.");
            this.setEnabled(false);
            return;
        }
        for (ExternalConnector connector : connections.values()) {
            connector.addBindingData(this);
            connector.addBindingTypes(this);
            connector.addCostTypes(this);
            connector.addScrollTypes(this);
            connector.addWarps(this);
        }
        readConfig();
        readWarps();
    }

    public void saveToDisk() {
        writeConfig();
        writeWarps();
    }

    @Override
    protected void preDisable() {
        super.preDisable();
        writeConfig();
        writeWarps();
    }

    public void registerContent(Plugin plugin, ExternalConnector connector) {
        this.connections.put(plugin.getName(), connector);
    }
}
