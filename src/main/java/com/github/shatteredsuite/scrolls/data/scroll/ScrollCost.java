package com.github.shatteredsuite.scrolls.data.scroll;

import static com.github.shatteredsuite.core.config.ConfigUtil.getInEnum;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@SerializableAs("ScrollCost")
public class ScrollCost implements ConfigurationSerializable {

    public final CostType type;
    public final Object data;

    public ScrollCost(CostType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public static ScrollCost deserialize(Map<String, Object> map) {
        CostType type = getInEnum(map, "type", CostType.class, CostType.NONE);
        Object data = null;
        switch (type) {
            case XP:
            case HUNGER:
            case HEALTH:
                if (map.containsKey("data") && map.get("data") instanceof Integer) {
                    data = map.get("data");
                }
                else {
                    data = 5;
                }
                break;
            case POTION:
                if (map.containsKey("data") && map.get("data") instanceof PotionEffect) {
                    data = map.get("data");
                }
                else {
                    data = new PotionEffect(PotionEffectType.BLINDNESS, 5, 0);
                }
                break;
        }
        return new ScrollCost(type, data);
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        result.put("type", type.name());
        result.put("data", data);
        return result;
    }

    public enum CostType {
        NONE("NONE"),
        XP("XP"),
        HUNGER("HUNGER"),
        HEALTH("HEALTH"),
        POTION("POTION");

        public final String value;

        CostType(String value) {
            this.value = value;
        }

        public static CostType fromString(String value) {
            for (CostType type : CostType.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }
    }
}
