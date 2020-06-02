package com.github.shatteredsuite.scrolls.recipe

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object RecipeHandler {
    @JvmStatic
    fun registerRecipes(instance: ShatteredScrolls) {
        for (type in instance.scrolls().all) {
            if (type.crafting.craftable) {
                val configRecipe = type.crafting.recipe
                val key = NamespacedKey(instance, type.id + "_crafting")
                val recipe = ShapedRecipe(key, ScrollInstance(type, type.defaultCharges, type.infinite, type.bindingData).toItemStack())
                for ((key1, value) in configRecipe!!.mapping) {
                    recipe.setIngredient(key1!!, value!!)
                }
                recipe.shape(configRecipe.items.joinToString(""))
                Bukkit.addRecipe(recipe)
            }
            if (type.crafting.repairable) {
                for (i in 1..8) {
                    val key = NamespacedKey(instance, type.id + "_repair_" + i)
                    val recipe = ShapelessRecipe(key, ScrollInstance(type, type.defaultCharges, type.infinite, type.bindingData).toItemStack())
                    recipe.addIngredient(i, type.crafting.repairMaterial!!)
                    Bukkit.addRecipe(recipe)
                }
            }
        }
    }
}