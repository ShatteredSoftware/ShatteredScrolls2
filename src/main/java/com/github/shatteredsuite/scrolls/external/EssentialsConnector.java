package com.github.shatteredsuite.scrolls.external;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.commands.WarpNotFoundException;
import com.github.shatteredsuite.core.util.ExternalProvider;
import com.github.shatteredsuite.scrolls.ShatteredScrolls;
import com.github.shatteredsuite.scrolls.data.warp.Warp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.ess3.api.InvalidWorldException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EssentialsConnector extends ExternalConnector {

    private final Essentials essentials;

    public EssentialsConnector(Essentials essentials) {
        this.essentials = essentials;
    }

    @Override
    public void addWarps(ShatteredScrolls instance) {
        instance.warps().addSource("essentials", new ExternalProvider<Warp>() {
            @Nullable
            @Override
            public Warp get(@NotNull String s) {
                try {
                    return new Warp(s, s, essentials.getWarps().getWarp(s), true);
                } catch (WarpNotFoundException | InvalidWorldException e) {
                    return null;
                }
            }

            @Override
            public boolean has(@NotNull String s) {
                try {
                    return essentials.getWarps().getWarp(s) != null;
                } catch (WarpNotFoundException | InvalidWorldException e) {
                    return false;
                }
            }

            @NotNull
            @Override
            public Iterable<Warp> all() {
                Iterable<String> keys = keys();
                List<Warp> warps = new ArrayList<>();
                for(String s : keys) {
                    Warp warp = get(s);
                    if(warp == null) {
                        continue;
                    }
                    warps.add(warp);
                }
                return warps;
            }

            @NotNull
            @Override
            public Iterable<String> keys() {
                return essentials.getWarps().getList();
            }
        });
    }
}
