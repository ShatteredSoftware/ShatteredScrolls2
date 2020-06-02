package com.github.shatteredsuite.scrolls.external;

import com.earth2me.essentials.Essentials;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;

public class EssentialsConnector extends ExternalConnector {

    private final Essentials essentials;

    public EssentialsConnector(Essentials essentials) {
        this.essentials = essentials;
    }

    @Override
    public void addWarps(ShatteredScrolls instance) {
        for (String name : essentials.getWarps().getList()) {
            try {
                instance.warps()
                    .create("essentials:" + name, name, essentials.getWarps().getWarp(name), true);
            } catch (Exception ex) {
                // Continue
            }
        }
    }
}
