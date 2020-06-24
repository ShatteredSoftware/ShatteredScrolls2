package com.github.shatteredsuite.scrolls.data

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.google.gson.annotations.SerializedName
import org.bukkit.World
import kotlin.collections.ArrayList

open class ScrollConfig(@JvmField @SerializedName("defaultType") val defaultTypeName: String, @JvmField val safetyCheck: Boolean, @JvmField val cooldown: Int, cancelMode: ScrollCancelMode? = ScrollCancelMode.UNBIND, @JvmField val allowedWorlds: List<String>, vararg scrolls: ScrollType) {
    @JvmField
    val cancelMode: ScrollCancelMode

    @JvmField
    val scrollTypes: ArrayList<ScrollType> = ArrayList()

    init {
        scrollTypes.addAll(scrolls)
        this.cancelMode = cancelMode ?: ScrollCancelMode.UNBIND
    }

    fun getDefaultType() : ScrollType {
        return ShatteredScrolls.getInstance().scrolls()[defaultTypeName]
    }
}