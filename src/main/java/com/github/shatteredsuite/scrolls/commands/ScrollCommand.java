package com.github.shatteredsuite.scrolls.commands;

import com.github.shatteredsuite.core.commands.WrappedCommand;
import com.github.shatteredsuite.core.messages.Messageable;

public class ScrollCommand extends WrappedCommand {

    public ScrollCommand(Messageable instance, WrappedCommand parent, String label,
        String permission, String helpPath) {
        super(instance, parent, label, permission, helpPath);
    }
}
