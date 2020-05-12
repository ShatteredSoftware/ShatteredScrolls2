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
     * @param id The ID to register this with. Used to look it up.
     * @param warp The warp to register.
     */
    void register(String id, Warp warp);

    /**
     * Creates and registers a warp.
     *
     * @param id The ID to register this with.
     * @param name The name of the warp, shown to players.
     * @param location The location of the warp.
     */
    default void create(String id, String name, Location location) {
        Warp warp = new Warp(name, location);
        register(id, warp);
    }
}
