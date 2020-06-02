package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.data.warp.Warp;
import org.bukkit.Location;

/**
 * API for accessing and creating warps.
 */
@SuppressWarnings("unused")
public interface WarpAPI {

    /**
     * Gets one warp based on its ID.
     *
     * @param id The ID to look up.
     * @return The warp, if it exists.
     */
    Warp get(String id);

    /**
     * Registers the given warp. Overwrites existing warps with the same ID.
     *
     * @param warp The warp to register.
     */
    void register(Warp warp);

    Iterable<Warp> getAll();

    /**
     * Creates and registers a warp. Defaults to external (meaning it won't be written to the
     * config.)
     *
     * @param id       The ID to register this with.
     * @param name     The name of the warp, shown to players.
     * @param location The location of the warp.
     */
    default void create(String id, String name, Location location) {
        create(id, name, location, true);
    }

    /**
     * Creates and registers a warp.
     *
     * @param id       The ID to register this with.
     * @param name     The name of the warp, shown to players.
     * @param location The location of the warp.
     * @param external Whether this should be saved to the config.
     */
    default void create(String id, String name, Location location, boolean external) {
        Warp warp = new Warp(id, name, location, external);
        register(warp);
    }
}
