package com.github.shatteredsuite.scrolls.extensions

import org.bukkit.ChatColor

val String.colored: String
    get() = ChatColor.translateAlternateColorCodes('&', this)

val List<String>.colored: List<String>
    get() = this.map { it.colored }