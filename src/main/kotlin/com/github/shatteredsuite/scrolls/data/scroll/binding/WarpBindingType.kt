package com.github.shatteredsuite.scrolls.data.scroll.binding

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import org.bukkit.command.CommandSender
import org.bukkit.util.StringUtil

class WarpBindingType : BindingType("warp") {
    override fun deserialize(map: Map<String?, Any?>): BindingData {
        val rawWarp: Any? = map["warp-id"] ?: return UnboundBindingData()
        if (rawWarp !is String) {
            return UnboundBindingData()
        }
        return WarpBindingData(ShatteredScrolls.getInstance().warps().get(rawWarp))
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): BindingData {
        if(args.isEmpty()) {
            throw ArgumentValidationException("Cannot construct a warp from nothing.", ArgumentValidationException.ValidationErrorType.NOT_ENOUGH_ARGS, "not-enough-args", null)
        }
        val inst = ShatteredScrolls.getInstance().warps().get(args[0])
        if(inst != null) {
            return WarpBindingData(inst)
        }
        throw ArgumentValidationException("Cannot construct a warp from ${args[0]}.", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-warp", args[0], ShatteredScrolls.getInstance().warps().ids.joinToString())
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): List<String> {
        val options = ShatteredScrolls.getInstance().warps().ids
        val results = mutableListOf<String>()
        StringUtil.copyPartialMatches(args[0], options, results)
        return results.sorted()
    }

    override fun fromNBT(compound: NBTCompound): BindingData {
        val rawWarp: Any? = compound.getString("warp-id") ?: return UnboundBindingData()
        if (rawWarp !is String) {
            return UnboundBindingData()
        }
        return WarpBindingData(ShatteredScrolls.getInstance().warps().get(rawWarp))
    }
}
