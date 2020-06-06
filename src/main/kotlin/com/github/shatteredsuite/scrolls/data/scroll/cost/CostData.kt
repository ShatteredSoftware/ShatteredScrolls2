package com.github.shatteredsuite.scrolls.data.scroll.cost

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollInteractor
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import com.google.gson.*
import java.lang.reflect.Type

abstract class CostData(val type: String) : ScrollInteractor {
    abstract fun serialize() : Any
}

class CostDataSerializer(private val pluginInstance: ShatteredScrolls) : JsonSerializer<CostData> {
    override fun serialize(src: CostData?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val element = JsonObject()
        element.add("type", pluginInstance.gson.toJsonTree(src?.type ?: "Unbound"))
        element.add("data", pluginInstance.gson.toJsonTree(src?.serialize() ?: mapOf<String, Any>()))
        return element
    }
}

class CostDataDeserializer(private val pluginInstance: ShatteredScrolls) : JsonDeserializer<CostData> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CostData {
        val obj = json?.asJsonObject ?: return NoneCostData()
        val type = obj.get("type").asString
        val cType = pluginInstance.costTypes()[type]
        return if(obj.has("data")) {
            cType.deserialize(pluginInstance.gson.fromJson(obj.get("data"), Map::class.java) as Map<String?, Any?>)
        }
        else {
            cType.deserialize(mapOf<String, Any>())
        }
    }
}