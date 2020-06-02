package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.core.commands.WrappedCommand;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ScrollCommand extends WrappedCommand {

    private final ShatteredScrolls instance;

    public ScrollCommand(ShatteredScrolls instance) {
        super(instance, null, "scrolls", "shatteredscrolls.command.scroll", "command.scroll");
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            ItemStack item = instance.scrolls().getAll().iterator().next().createInstance()
                .toItemStack();
            ((Player) sender).getInventory().addItem(item);

        }
        for (ScrollType type : instance.scrolls().getAll()) {
            sender.sendMessage(type.getName());
        }
        return true;
    }
}
