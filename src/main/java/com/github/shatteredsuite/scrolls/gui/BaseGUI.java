package com.github.shatteredsuite.scrolls.gui;

import com.github.shatteredsuite.core.include.inv.ClickableItem;
import com.github.shatteredsuite.core.include.inv.SmartInventory;
import com.github.shatteredsuite.core.include.inv.content.InventoryContents;
import com.github.shatteredsuite.core.include.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class BaseGUI implements InventoryProvider {
    public static final SmartInventory main = SmartInventory.builder()
        .id("teleportationscrolls::main")
        .type(InventoryType.CHEST)
        .size(1, 9)
        .title(ChatColor.DARK_GRAY + "Teleportation Scrolls")
        .provider(new BaseGUI())
        .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        inventoryContents.set(1, 1, ClickableItem.of(new ItemStack(Material.PAPER, 1),
            e -> {
                player.closeInventory();
                ScrollsMenu.menu.open(player);
            }
        ));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}