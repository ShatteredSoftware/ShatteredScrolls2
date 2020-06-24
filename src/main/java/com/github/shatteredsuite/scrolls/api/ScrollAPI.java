package com.github.shatteredsuite.scrolls.api;

import com.github.shatteredsuite.scrolls.data.scroll.ScrollType;
import com.github.shatteredsuite.scrolls.items.ScrollInstance;

/**
 * API for accessing and creating scrolls.
 */
@SuppressWarnings("unused")
public interface ScrollAPI extends AbstractAPI<ScrollType> {

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
