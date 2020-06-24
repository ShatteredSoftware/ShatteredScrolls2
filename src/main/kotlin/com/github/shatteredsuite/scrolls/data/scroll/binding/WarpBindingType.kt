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
        return WarpBindingData(ShatteredScrolls.getInstance().warps()[rawWarp])
    }

    override fun createFromCommandArgs(args: Array<out String>, sender: CommandSender): BindingData {
        if(args.isEmpty()) {
            throw ArgumentValidationException("Cannot construct a warp from nothing.", ArgumentValidationException.ValidationErrorType.NOT_ENOUGH_ARGS, "not-enough-args", null)
        }
        var inst = ShatteredScrolls.getInstance().warps()[args[0]]
        if(inst != null) {
            return WarpBindingData(inst)
        }
        throw ArgumentValidationException("Cannot construct a warp from ${args[0]}.", ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-warp", args[0])
    }

    override fun tabCompleteCommandArgs(args: Array<out String>, sender: CommandSender): MutableList<String> {
        val options = ShatteredScrolls.getInstance().warps().all.map { it.id }
        val results = mutableListOf<String>()
        if(options.isEmpty()) {
            return results
        }
        StringUtil.copyPartialMatches(args[0], options, results)
        return results.sorted() as MutableList<String>
    }

    override fun fromNBT(compound: NBTCompound): BindingData {
        val rawWarp: Any? = compound.getString("warp-id") ?: return UnboundBindingData()
        if (rawWarp !is String) {
            return UnboundBindingData()
        }
        return WarpBindingData(ShatteredScrolls.getInstance().warps()[rawWarp])
    }
}
