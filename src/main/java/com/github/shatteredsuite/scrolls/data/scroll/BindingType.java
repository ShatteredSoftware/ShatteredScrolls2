package com.github.shatteredsuite.scrolls.data.scroll;

import java.util.HashMap;
import java.util.Map;

/**
 * A scroll-unspecific version of binding data that represents the high-level interaction of a scroll,
 * and provides the functionality to create scroll-specific versions.
 *
 * @see BindingData
 */
public abstract class BindingType implements NBTReader {

    public final String key;

    public BindingType(String key) {
        this.key = key;
    }

    /**
     * Creates BindingData from a map, normally from a config.
     *
     * @param map The map to deserialize from.
     * @return The built BindingData.
     */
    public abstract BindingData deserialize(Map<String, Object> map);
}
