package com.github.shatteredsuite.scrolls;

import com.github.shatteredsuite.core.config.ConfigRecipe;
import com.github.shatteredsuite.core.include.xseries.XMaterial;
import com.github.shatteredsuite.scrolls.data.DefaultScrollConfig;
import com.github.shatteredsuite.scrolls.data.ScrollCancelMode;
import com.github.shatteredsuite.scrolls.data.ScrollConfig;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostData;
import com.github.shatteredsuite.scrolls.data.scroll.cost.CostType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;

public class ConfigManager {
    public static List<Warp> loadWarps(ShatteredScrolls instance) {
        return readWarps(instance);
    }

    public static ScrollConfig loadConfig(ShatteredScrolls instance) {
        if(isV1(instance)) {
            writeFromV1(instance);
        }
        if(!isConfig(instance)) {
            writeDefaultConfig(instance);
        }
        return readConfig(instance);
    }

    public static void save(ShatteredScrolls instance) {
        writeConfig(instance.config(), instance);
        writeWarps(Lists.newArrayList(instance.warps().getAll()), instance);
    }

    private static void writeFromV1(ShatteredScrolls instance) {
        fromV1Warps(instance);
        fromV1Config(instance);
    }

    private static boolean isConfig(ShatteredScrolls instance) {
        File config = new File(instance.getDataFolder(), "config.json");
        return config.exists();
    }


    private static ScrollConfig readConfig(ShatteredScrolls instance) {
        File file = new File(instance.getDataFolder(), "config.json");
        ScrollConfig scrollConfig = null;
        boolean invalid = false;
        try {
            scrollConfig = instance.gson.fromJson(new FileReader(file), ScrollConfig.class);
            if(scrollConfig == null) {
                invalid = true;
            }
        } catch (FileNotFoundException e) {
            invalid = true;
        }
        if(invalid) {
            scrollConfig = DefaultScrollConfig.getConfig();
        }
        return scrollConfig;
    }

    private static void stripSerializables(File file) {
        StringBuilder builder = new StringBuilder();
        try {
            FileReader reader = new FileReader(file);
            int read;
            while((read = reader.read()) != -1) {
                builder.append((char) read);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = builder.toString();
        text = text.replaceAll("(?m)^\\s*==: ?(?:ScrollConfig|ScrollCost|ScrollRecipe|ScrollLocation).*(?:\\r?\\n)?", "");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeDefaultConfig(ShatteredScrolls instance) {
        instance.getLogger().warning("Config invalid or not found. Generating a default one.");
        ScrollConfig scrollConfig = DefaultScrollConfig.getConfig();
        writeConfig(scrollConfig, instance);
    }

    private static void writeConfig(ScrollConfig config, ShatteredScrolls instance) {
        try {
            String configText = instance.gson.toJson(config);
            Writer writer = new OutputStreamWriter(new FileOutputStream(new File(instance.getDataFolder(), "config.json")), StandardCharsets.UTF_8);
            writer.write(configText);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<Warp> readWarps(ShatteredScrolls instance) {
        if (!instance.getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            instance.getDataFolder().mkdirs();
        }
        File warpsFile = new File(instance.getDataFolder(), "warps.json");
        ArrayList<Warp> warps;
        Type type = new TypeToken<ArrayList<Warp>>(){}.getType();
        try {
            warps = instance.gson.fromJson(new FileReader(warpsFile), type);
        } catch (FileNotFoundException e) {
            warps = new ArrayList<>();
        }
        return warps;
    }

    private static void writeWarps(List<Warp> warps, ShatteredScrolls instance) {
        if (!instance.getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            instance.getDataFolder().mkdirs();
        }
        warps = warps.stream().filter(it -> !it.getExternal()).collect(Collectors.toList());
        File file = new File(instance.getDataFolder(), "warps.json");
        try {
            String configText = instance.gson.toJson(warps);
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(configText);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean isV1(ShatteredScrolls instance) {
        File config = new File(instance.getDataFolder(), "config.yml");
        return config.exists();
    }

    private static void fromV1Warps(ShatteredScrolls instance) {
        File warps = new File(instance.getDataFolder(), "locations.yml");
        File backup = new File(instance.getDataFolder(), "locations-old.yml");
        FileUtil.copy(warps, backup);
        stripSerializables(warps);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(warps);
        ConfigurationSection section = config.getConfigurationSection("locations");
        if(section == null) {
            return;
        }
        List<Warp> warpList = new ArrayList<>();
        for(String key : section.getKeys(false)) {
            ConfigurationSection location = section.getConfigurationSection(key);
            if(location == null) {
                continue;
            }
            String id = location.getString("id");
            if(id == null) {
                continue;
            }
            String name = location.getString("name");
            if(name == null) {
                continue;
            }
            Location loc = location.getSerializable("location", Location.class);
            if(loc == null) {
                continue;
            }
            warpList.add(new Warp(id, name, loc, false));
        }
        //noinspection ResultOfMethodCallIgnored
        warps.delete();
        writeWarps(warpList, instance);
    }

    private static void fromV1Config(ShatteredScrolls instance) {
        File configFile = new File(instance.getDataFolder(), "config.yml");
        File messageFile = new File(instance.getDataFolder(), "messages.yml");
        File backup = new File(instance.getDataFolder(), "config-old.yml");
        stripSerializables(configFile);
        FileUtil.copy(messageFile, new File(instance.getDataFolder(), "messages-old.yml"));
        FileUtil.copy(configFile, backup);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        ScrollConfig scrollConfig = fromV1Yaml(config, instance);
        //noinspection ResultOfMethodCallIgnored
        configFile.delete();
        //noinspection ResultOfMethodCallIgnored
        messageFile.delete();
        extractMessages(instance);
        writeConfig(scrollConfig, instance);
    }

    private static void extractMessages(ShatteredScrolls instance) {
        instance.saveResource("messages.yml", true);
    }

    private static ScrollConfig fromV1Yaml(YamlConfiguration config, ShatteredScrolls instance) {
        ConfigurationSection section = config.getConfigurationSection("config");
        if(section == null) {
            return DefaultScrollConfig.getConfig();
        }
        HashMap<String, BindingDisplay> displays = getDisplaysFromV1(section, instance);
        ScrollCrafting crafting = getCraftingFromV1(section, instance);
        CostData cost = getCostFromV1(section, instance);
        int charges = section.getInt("charges", 5);
        int cooldown = section.getInt("cooldown", 5000);
        Material material = XMaterial.matchXMaterial(section.getString("scroll-material", "PAPER")).orElse(XMaterial.PAPER).parseMaterial();
        ScrollType type = new ScrollType("LegacyScroll", "Legacy Scroll", material,
            new UnboundBindingData(), displays, crafting, cost, false, charges);
        return new ScrollConfig("LegacyScroll", false, cooldown, ScrollCancelMode.UNBIND, DefaultScrollConfig.getConfig().allowedWorlds, type);
    }

    private static CostData getCostFromV1(ConfigurationSection section, ShatteredScrolls instance) {
        ConfigurationSection cost = section.getConfigurationSection("cost");
        if(cost == null) {
            return new NoneCostData();
        }
        String type = cost.getString("type");
        CostType costType = instance.costTypes().get(type.toLowerCase());
        if(costType == null) {
            instance.getLogger().warning("Invalid Cost type. Using default.");
            return new NoneCostData();
        }
        return costType.deserialize(cost.get("data"));
    }

    private static ScrollCrafting getCraftingFromV1(ConfigurationSection section, ShatteredScrolls instance) {
        int amount = section.getInt("recipe.amount", 1);
        List<String> recipe = section.getStringList("recipe.recipe");
        HashMap<String, String> map = new HashMap<>();
        for(String key : section.getConfigurationSection("recipe.mapping").getKeys(false)) {
            map.put(key, section.getString("recipe.mapping." + key));
        }
        return new ScrollCrafting(new ConfigRecipe(recipe, map), Material.AIR, 0, amount);
    }

    private static HashMap<String, BindingDisplay> getDisplaysFromV1(ConfigurationSection section, ShatteredScrolls instance) {
        String unboundName = section.getString("scroll-unbound-name", "&bUnbound Teleportation Scroll");
        String unboundLore = section.getString("scroll-unbound-lore",
            "&8---=[ &7Description &8]=---\n" +
                "&7An unbound teleportation scroll.\n" +
                "&7Right click it to bind it to your location.\n" +
                "&7It has &f%charges%&7 charges.");
        String warpName= section.getString("scroll-bound-location-name", "&bTeleportation Scroll");
        String warpLore = section.getString("scroll-bound-location-lore",
            "&8---=[ &7Description &8]=---\n"
            + "&7A bound teleportation scroll.\n"
            + "&7It goes to &f%x% %y% %z%&7 in &f%world%&7.\n"
            + "&7It has &f%charges%&7 charges remaining.\n");
        String locationName = section.getString("scroll-bound-position-name", "&bTeleportation Scroll");
        String locationLore = section.getString("scroll-bound-position-lore",
            "&8---=[ &7Description &8]=---\n"
                + "&7A bound teleportation scroll.\n"
                + "&7It goes to &f%destination%&7.\n"
                + "&7It has &f%charges%&7 charges remaining.\n");
        boolean boundGlow = section.getBoolean("scroll-bound-glow", true);
        boolean unboundGlow = section.getBoolean("scroll-unbound-glow", false);
        int customModelData = section.getInt("model", 0);

        BindingDisplay unbound = new BindingDisplay(unboundName, false,
            Arrays.asList(unboundLore.split("\n")), unboundGlow, customModelData);
        BindingDisplay warp = new BindingDisplay(warpName, false,
            Arrays.asList(warpLore.replaceAll("%destination%", "%name%").split("\n")),
            boundGlow, customModelData);
        BindingDisplay location = new BindingDisplay(locationName, false,
            Arrays.asList(locationLore.split("\n")), boundGlow, customModelData);

        HashMap<String, BindingDisplay> displays = new HashMap<>();
        displays.put("unbound", unbound);
        displays.put("warp", warp);
        displays.put("location", location);
        return displays;
    }
}
