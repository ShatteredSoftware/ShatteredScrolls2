package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.util.StringUtil
import com.github.shatteredsuite.scrolls.api.ScrollAPI
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import java.util.*

class ScrollTypeManager : ScrollAPI {
    private val scrollTypes = LinkedHashMap<String, ScrollType>()
    override fun register(id: String, type: ScrollType) {
        if (type == null) {
            return
        }
        if (StringUtil.isEmptyOrNull(id)) {
            return
        }
        scrollTypes[id] = type
    }

    override fun get(id: String): ScrollType {
        return scrollTypes[id]!!
    }

    override fun getAll(): Iterable<ScrollType> {
        return scrollTypes.values
    }

    override fun createInstance(type: ScrollType): ScrollInstance {
        return type.createInstance()
    }
}