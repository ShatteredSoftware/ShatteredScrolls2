package com.github.shatteredsuite.scrolls.data.warp

import com.github.shatteredsuite.scrolls.data.Identified
import org.bukkit.Location

class Warp(override val id: String, val name: String, val location: Location, val external: Boolean = true) : Identified
