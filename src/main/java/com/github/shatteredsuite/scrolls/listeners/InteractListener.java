package com.github.shatteredsuite.scrolls.listeners;

import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final ShatteredScrolls instance;

    public InteractListener(ShatteredScrolls instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }
        if (event.getItem().getType() == Material.AIR) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_AIR
            && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        ScrollInstance instance = ScrollInstance.fromItemStack(event.getItem());
        if (instance == null) {
            return;
        }
        if (!event.getPlayer().hasPermission("shatteredscrolls.scroll.use")) {
            return;
        }
        if (!this.instance.cooldownManager.canUse(event.getPlayer().getUniqueId())) {
            return;
        }
        event.getItem().setAmount(event.getItem().getAmount() - 1);
        ScrollInstance result = instance.getScrollType().getCost()
            .onInteract(instance, event.getPlayer()).getBindingData()
            .onInteract(instance, event.getPlayer());
        if (result.getCharges() > 0 || result.isInfinite()) {
            event.getPlayer().getInventory().addItem(result.toItemStack());
        }
        this.instance.cooldownManager.use(event.getPlayer().getUniqueId());
    }
}
