package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.data.Identified

abstract class CostType(override var id: String) : Identified {
    abstract fun deserialize(data: Any): CostData
}