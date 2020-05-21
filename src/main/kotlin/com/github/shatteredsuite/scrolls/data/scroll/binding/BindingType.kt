package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.scrolls.data.Identified
import com.github.shatteredsuite.scrolls.data.scroll.NBTReader

/**
 * A scroll-unspecific version of binding data that represents the high-level interaction of a scroll,
 * and provides the functionality to create scroll-specific versions.
 *
 * @see BindingData
 */
abstract class BindingType(override val id: String) : NBTReader, Identified {

    /**
     * Creates BindingData from a map, normally from a config.
     *
     * @param map The map to deserialize from.
     * @return The built BindingData.
     */
    abstract fun deserialize(map: Map<String?, Any?>): BindingData
}