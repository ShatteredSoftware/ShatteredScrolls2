package com.github.shatteredsuite.scrolls.listeners;

import com.github.shatteredsuite.scrolls.ShatteredScrolls2;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import javax.swing.SpringLayout;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final ShatteredScrolls2 instance;

    public InteractListener(ShatteredScrolls2 instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {
        if(event.getItem().getType() == Material.AIR) {
            return;
        }
        ScrollInstance instance = ScrollInstance.fromItemStack(event.getItem());
        if(instance == null) {
            return;
        }
        event.getItem().setAmount(event.getItem().getAmount() - 1);
        if(!event.getPlayer().hasPermission("shatteredscrolls.scroll.use")) {
            return;
        }
        if(!this.instance.cooldownManager.canUse(event.getPlayer().getUniqueId())) {
            return;
        }
        ScrollInstance result = instance.scrollType.getCost().onInteract(instance, event.getPlayer()).bindingData.onInteract(instance,
            event.getPlayer());
        event.getPlayer().getInventory().addItem(result);
        this.instance.cooldownManager.use(event.getPlayer().getUniqueId());
    }
}
