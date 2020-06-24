package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.core.util.Identified

abstract class CostType(override var id: String) : Identified {
    abstract fun deserialize(data: Any?): CostData
}