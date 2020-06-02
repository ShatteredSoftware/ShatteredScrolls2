package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;

/**
 * API for accessing and creating scrolls.
 */
@SuppressWarnings("unused")
public interface ScrollAPI {

    /**
     * Registers the given scroll type.
     *
     * @param id   The ID to register this with. Used to look it up.
     * @param type The scroll type to register.
     */
    void register(String id, ScrollType type);

    /**
     * Get one scroll type based on its ID.
     *
     * @param id The ID of the scroll to look up.
     * @return The scroll type if it exists, <code>null</code> otherwise.
     */
    ScrollType get(String id);

    Iterable<ScrollType> getAll();

    /**
     * Alias to {@link #createInstance(ScrollType)} called with {@link #get(String)}.
     *
     * @param id The ID to look up.
     * @return A scroll instance, if ID exists. Null otherwise.
     */
    default ScrollInstance createInstance(String id) {
        return createInstance(get(id));
    }

    /**
     * Creates an item instance of a scroll type. Should respond to null parameters properly.
     *
     * @param type The type of the scroll to transform into an instance.
     * @return The instance if type could be transformed properly, <code>null</code> otherwise.
     */
    ScrollInstance createInstance(ScrollType type);
}
