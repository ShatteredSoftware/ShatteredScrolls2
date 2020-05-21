package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.external.ExternalConnector;
import org.bukkit.plugin.Plugin;

public interface ContentAPI {
    void registerContent(Plugin plugin, ExternalConnector connector);
}
