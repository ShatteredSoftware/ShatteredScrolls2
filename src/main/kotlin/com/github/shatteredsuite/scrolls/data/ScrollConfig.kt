package com.github.shatteredsuite.scrolls.data

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

class ScrollConfig(@JvmField @SerializedName("defaultType") val defaultTypeName: String, @JvmField val safetyCheck: Boolean, @JvmField val cooldown: Int, vararg scrolls: ScrollType) {
    val defaultType: ScrollType by lazy {
        ShatteredScrolls.getInstance().scrolls()[defaultTypeName]
    }

    @JvmField
    val scrollTypes: ArrayList<ScrollType> = ArrayList()

    init {
        scrollTypes.addAll(scrolls)
    }

    override fun toString(): String {
        return "ScrollConfig(defaultType=$defaultType, safetyCheck=$safetyCheck, cooldown=$cooldown)"
    }
}