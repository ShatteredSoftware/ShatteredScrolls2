package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.core.commands.WrappedCommand;
import com.github.shatteredsuite.core.messages.Messageable;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollCrafting;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingData;
import com.github.shatteredsuite.scrolls.data.scroll.binding.BindingDisplay;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingData;
import com.github.shatteredsuite.scrolls.data.scroll.binding.UnboundBindingType;
import com.github.shatteredsuite.scrolls.data.scroll.cost.NoneCostData;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScrollCommand extends WrappedCommand {

    public ScrollCommand(Messageable instance, WrappedCommand parent, String label,
        String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            ((Player) sender).getInventory().addItem(new ScrollInstance(new ScrollType(
                "BindingScroll",
                "Unbound Scroll",
                Material.PAPER,
                0,
                false,
                new UnboundBindingData(),
                new HashMap<>(),
                new ScrollCrafting(),
                new NoneCostData(),
                false,
                5
            ),
                5, false, new UnboundBindingData()));
        }
        return true;
    }
}
