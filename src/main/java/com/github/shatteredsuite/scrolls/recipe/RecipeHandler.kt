package com.github.shatteredsuite.scrolls.recipe

import com.github.shatteredsuite.scrolls.ShatteredScrolls
import com.github.shatteredsuite.scrolls.items.ScrollInstance
import org.bukkit.Bukkit
import org.bukkit.inventory.ShapedRecipe

object RecipeHandler {
    @JvmStatic
    fun registerRecipes(instance: ShatteredScrolls) {
        for (type in instance.scrolls().all) {
            if (type.crafting.craftable) {
                val configRecipe = type.crafting.recipe ?: continue
                val recipe = ShapedRecipe(type.crafting.key
                        ?: continue, ScrollInstance(type, type.defaultCharges, type.infinite, type.bindingData).toItemStack())
                recipe.group = "scrolls"
                recipe.shape(configRecipe.items[0], configRecipe.items[1], configRecipe.items[2])
                for ((key1, value) in configRecipe.mapping) {
                    recipe.setIngredient(key1!!, value!!)
                }
                Bukkit.addRecipe(recipe)
            }
        }
    }
}