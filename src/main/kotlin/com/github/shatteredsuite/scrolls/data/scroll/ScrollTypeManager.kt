package com.github.shatteredsuite.scrolls.data.scroll

import com.github.shatteredsuite.core.util.Manager
import com.github.shatteredsuite.core.util.StringUtil
import com.github.shatteredsuite.scrolls.api.ScrollAPI
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import java.util.*

class ScrollTypeManager : Manager<ScrollType>(), ScrollAPI {
    override fun createInstance(type: ScrollType): ScrollInstance {
        return type.createInstance()
    }
}