package com.github.shatteredsuite.scrolls.ext

import org.bukkit.plugin.PluginDescriptionFile


val PluginDescriptionFile.placeholders: Map<out String, String>
    get() {
        val map = mutableMapOf("version" to this.version, "name" to this.name, "authors" to this.authors.joinToString())
        if(apiVersion != null)
            map["api-version"] = apiVersion!!
        if(description != null)
            map["description"] = description!!
        return map
    }