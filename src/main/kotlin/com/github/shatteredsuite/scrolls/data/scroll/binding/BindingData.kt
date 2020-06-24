package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.NBTApplier
import com.github.shatteredsuite.scrolls.data.scroll.ScrollInteractor
import com.google.gson.*
import java.lang.reflect.Type

/**
 * Item-specific binding information, whether that's a warp, location, unbound, and contains all of
 * the information needed to handle an interaction.
 *
 * @see BindingType
 */
abstract class BindingData protected constructor(val type: String, @Transient val defaultDisplay: BindingDisplay) : ScrollInteractor, NBTApplier {
    override fun applyNBT(compound: NBTCompound): NBTCompound {
        val bindingCompound = compound.addCompound("binding")
        bindingCompound.setString("type", type)
        applyBindingNBT(bindingCompound)
        return compound
    }

    /**
     * Applies binding-specific NBT to the item.
     * @param compound A compound given where all binding-specific NBT should be applied.
     */
    protected abstract fun applyBindingNBT(compound: NBTCompound)

    /**
     * Converts this BindingData into a map, for use in configs.
     *
     * @return The converted map.
     */
    abstract fun serialize(): Map<String?, Any?>
    abstract fun parsePlaceholders(name: String): String
}

class BindingDataSerializer(private val pluginInstance: ShatteredScrolls) : JsonSerializer<BindingData> {
    override fun serialize(src: BindingData?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val element = JsonObject()
        element.add("type", pluginInstance.gson.toJsonTree(src?.type ?: "Unbound"))
        element.add("data", pluginInstance.gson.toJsonTree(src?.serialize() ?: mapOf<String, Any>()))
        return element
    }
}

class BindingDataDeserializer(private val pluginInstance: ShatteredScrolls) : JsonDeserializer<BindingData> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): BindingData {
        val obj = json?.asJsonObject ?: return UnboundBindingData()
        val type = obj.get("type").asString
        val bType = pluginInstance.bindingTypes()[type]
        return if(obj.has("data")) {
            bType.deserialize(pluginInstance.gson.fromJson(obj.get("data"), Map::class.java) as Map<String?, Any?>)
        }
        else {
            bType.deserialize(mapOf())
        }
    }
}