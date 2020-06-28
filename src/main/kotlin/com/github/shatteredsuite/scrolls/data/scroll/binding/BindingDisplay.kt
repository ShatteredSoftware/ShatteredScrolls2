package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.scrolls.extensions.colored
import com.google.gson.annotations.SerializedName

data class BindingDisplay(@SerializedName("name") private val rawName: String, val preserveName: Boolean, @SerializedName("lore") private val rawLore: MutableList<String>, val glow: Boolean = false, val customModelData: Int) {
    val name: String
        get() = rawName.colored

    val lore: MutableList<String>
        get() = rawLore.colored as MutableList<String>
}
