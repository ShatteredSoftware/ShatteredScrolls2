package com.github.shatteredsuite.scrolls.data

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.google.gson.annotations.SerializedName
import org.bukkit.World
import kotlin.collections.ArrayList

open class ScrollConfig(@JvmField @SerializedName("defaultType") var defaultTypeName: String, @JvmField val safetyCheck: Boolean, @JvmField val cooldown: Int, cancelMode: ScrollCancelMode? = ScrollCancelMode.UNBIND, @JvmField val allowedWorlds: List<String>, vararg scrolls: ScrollType) {
    var cancelMode: ScrollCancelMode? = cancelMode
        get() {
            if(field == null) {
                field = ScrollCancelMode.UNBIND
            }
            return field
        }
        private set

    @Transient
    var defaultType: ScrollType? = null
        get() {
            return ShatteredScrolls.getInstance().scrolls()[defaultTypeName]
        }
        private set

    @JvmField
    val scrollTypes: ArrayList<ScrollType> = ArrayList()

    init {
        scrollTypes.addAll(scrolls)
    }
}