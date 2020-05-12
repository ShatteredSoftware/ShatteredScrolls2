package com.github.shatteredsuite.scrolls.gui;

import com.github.shatteredsuite.core.include.inv.SmartInventory;
import com.github.shatteredsuite.core.include.inv.content.InventoryContents;
import com.github.shatteredsuite.core.include.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class ScrollsMenu implements InventoryProvider {
    public static final SmartInventory menu = SmartInventory.builder()
        .id("teleportationscrolls::scrolls")
        .title(ChatColor.DARK_GRAY + "Scrolls")
        .type(InventoryType.CHEST)
        .size(1, 9)
        .provider(new ScrollsMenu())
        .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
