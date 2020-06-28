package com.github.shatteredsuite.scrolls.items

import com.github.shatteredsuite.core.include.nbt.NBTCompound
import com.github.shatteredsuite.core.include.nbt.NBTItem
import com.github.shatteredsuite.core.include.xseries.XEnchantment
import com.github.shatteredsuite.core.include.xseries.XMaterial
import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

class ScrollInstance(val scrollType: ScrollType, val charges: Int, val isInfinite: Boolean, val bindingData: BindingData) {

    private var itemStack: ItemStack = ItemStack(this.scrollType.material)

    val placeholders: Map<String, String>
        get() {
            val map = mutableMapOf("charges" to chargesMessage)
            map.putAll(scrollType.placeholders)
            return map
        }

    val chargesMessage: String
        get() = if(isInfinite) ShatteredScrolls.getInstance().messenger.getMessage("infinite", mapOf())
        else charges.toString()

    private fun applyNBT() {
        val nbti = NBTItem(this.itemStack)
        val baseCompound = nbti.addCompound("shatteredscrolls")
        baseCompound.setString("type", scrollType.id)
        baseCompound.setInteger("charges", charges)
        baseCompound.setBoolean("infinite", isInfinite)
        baseCompound.setInteger("version", currentNbtVersion.nbtSpecifier)
        val binding = baseCompound.addCompound("binding")
        binding.setString("type", bindingData.type)
        bindingData.applyNBT(baseCompound)
        this.itemStack = nbti.item
    }

    private fun applyLore() {
        val meta = this.itemStack.itemMeta
        val type = scrollType
        val binding = this.bindingData
        val display = type.displays.getOrDefault(this.bindingData.type, type.bindingData.defaultDisplay)
        Objects.requireNonNull(meta) // Makes the IDE happy, even though Meta will never be null.
        if (!display.preserveName) {
            meta!!.setDisplayName(binding.parsePlaceholders(display.name).replace("%charges%", chargesMessage))
        }
        meta!!.lore = display.lore.map {
            binding.parsePlaceholders(it).replace("%charges%", chargesMessage)
        }
        meta.setCustomModelData(display.customModelData)
        if (display.glow) {
            if (type.material == XMaterial.BOW.parseMaterial()) {
                meta.addEnchant(Objects.requireNonNull(XEnchantment.DIG_SPEED.parseEnchantment())!!, 1, true)
            } else {
                meta.addEnchant(Objects.requireNonNull(XEnchantment.ARROW_INFINITE.parseEnchantment())!!, 1, true)
            }
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        this.itemStack.itemMeta = meta
    }

    fun toItemStack(): ItemStack {
        applyNBT()
        applyLore()
        return this.itemStack
    }

    companion object {
        val currentNbtVersion = NBTVersion.VERSION_2

        @JvmStatic
        fun fromItemStack(stack: ItemStack?): ScrollInstance? {
            if (stack == null) {
                return null
            }
            val item = NBTItem(stack)
            val version = getNBTVersion(item)
            return if (version != currentNbtVersion) {
                version.conversion?.convert(stack) ?: return null
            } else fromCurrentStack(item)
        }

        private fun getNBTVersion(item: NBTItem): NBTVersion {
            if (item.hasKey("shatteredscrolls")) {
                val compound = item.getCompound("shatteredscrolls")
                return getVersionFromCompound(compound)
            } else if (item.hasKey("shatteredscrolls_bound")) {
                return NBTVersion.VERSION_1
            }
            return NBTVersion.NONE
        }

        private fun getVersionFromCompound(compound: NBTCompound): NBTVersion {
            val version = compound.getInteger("version")
            return if (version == 2) {
                NBTVersion.VERSION_2
            } else NBTVersion.NONE
        }

        private fun fromCurrentStack(item: NBTItem): ScrollInstance {
            val comp = item.getCompound("shatteredscrolls")
            val scrollTypeName = comp.getString("type")
            val scrollType = ShatteredScrolls.getInstance().scrolls()[scrollTypeName] ?: ShatteredScrolls.getInstance().config().defaultType!!
            val charges = comp.getInteger("charges")
            val infinite = comp.getBoolean("infinite")
            val bindingData = readBindingData(comp)
            return ScrollInstance(scrollType, charges, infinite, bindingData)
        }

        private fun readBindingData(comp: NBTCompound): BindingData {
            val bindingData: BindingData
            val binding = comp.getCompound("binding")
            bindingData = if (binding != null) {
                val bindingName = binding.getString("type")
                ShatteredScrolls.getInstance().bindingTypes()[bindingName].fromNBT(binding)
            } else {
                UnboundBindingData()
            }
            return bindingData
        }
    }
}
